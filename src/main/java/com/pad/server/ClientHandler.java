package com.pad.server;

import com.pad.dto.Message;
import com.pad.dto.converter.GsonConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ClientHandler implements Runnable {

    public static final String STATUS_MSG_PBL = "MSG_PBL";
    public static final String STATUS_MSG_OK = "MSG_OK";
    public static final String STATUS_MSG_ERROR = "MSG_ERROR";

    private final MessageBroker messageBroker;
    private final Socket clientSocket;

    private BufferedReader input;
    private PrintWriter output;

    private static Map<String, Set<PrintWriter>> channelSubscribers = new ConcurrentHashMap<>();


    ClientHandler(MessageBroker broker, Socket clientSocket) {
        this.messageBroker = broker;
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            while (true) {
                if (input.ready()) {
                    String messageType = readMessageType();
                    Message message = GsonConverter.convertToDto(messageType, Message.class);
                    String channelName = message.getChannelName();

                    switch (message.getType()) {
                        case SEND_MESSAGE: {
                            System.out.println("Sending message");
                            messageBroker.addMessage(message);
                            publishMessage(message);
                        }
                        case READ_MESSAGE: {
                            processMessage(messageBroker.getMessage(channelName), STATUS_MSG_OK, output);
                        }
                        case CREATE_CHANNEL: {
                            messageBroker.createChannel(channelName);
                        }
                        case CLOSE_CHANNEL: {
                            messageBroker.deleteChannel(channelName);
                        }
                        case SUBSCRIBE: {
                            subscribeTo(channelName);
                            while (messageBroker.hasMessages(channelName)) {
                                processMessage(messageBroker.getMessage(channelName), STATUS_MSG_PBL, output);
                            }
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(Message message, String statusMsgPbl, PrintWriter output) {
        Message obtainedMessage = message;
        obtainedMessage.setStatus(statusMsgPbl);
        String serializedMessage = GsonConverter.convertToJson(obtainedMessage);
        output.println(serializedMessage);
        output.println();
        output.flush();
    }

    private void publishMessage(Message message) {
        for (Map.Entry<String, Set<PrintWriter>> entry : channelSubscribers.entrySet()) {
            String queue = entry.getKey();
            Set<PrintWriter> subs = entry.getValue();
            if (queue.equals(message.getChannelName())) {
                for (PrintWriter sub : subs) {
                    processMessage(message, STATUS_MSG_PBL, sub);
                }
            }
        }
    }

    private String readMessageType() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = input.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            stringBuilder.append(line);
        }
        String message = stringBuilder.toString();
        System.out.println(message);
        return message;
    }

    private void subscribeTo(String channelName) {
        channelSubscribers.putIfAbsent(channelName, new CopyOnWriteArraySet<>());
        Set<PrintWriter> outputOfSubscriber = channelSubscribers.get(channelName);
        outputOfSubscriber.add(output);
    }
}
