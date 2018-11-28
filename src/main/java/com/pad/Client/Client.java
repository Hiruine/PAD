package com.pad.Client;

import com.pad.dto.Message;
import com.pad.dto.MessageType;
import com.pad.dto.converter.JsonConverter;
import com.pad.ClientHandler;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable, Closeable {

    private String server;
    private int port;
    private Socket socket;
    private BufferedReader input;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile boolean closeRequested = false;
    private BlockingQueue<Message> messagesToConsume = new ArrayBlockingQueue<>(10);

    private PrintWriter output;


    private Map<String, Set<MessageObserver>> messageObservers = new HashMap<>();


    public Client(String server, int port) {
        this.server = server;
        this.port = port;
    }


    @Override
    public void close() throws IOException {
        closeRequested = true;
        socket.close();
        output.close();
        executorService.shutdown();
    }

    @Override
    public void run() {
        while (!closeRequested) {
            try {
                String serializedMessage = readMessageType();
                Message message = JsonConverter.convertToDto(serializedMessage, Message.class);
                if (message.getStatus().equals(ClientHandler.STATUS_MSG_PBL)) {
                    messageObservers.get(message.getChannelName()).forEach(a -> a.consumeMessage(message));
                } else {
                    messagesToConsume.put(message);
                }
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String readMessageType() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = input.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            sb.append(line);
        }
        String command = sb.toString();
        return command;
    }

    public void subscribe(String channelName, MessageObserver messageObserver) {
        messageObservers.putIfAbsent(channelName, new HashSet<>());
        messageObservers.get(channelName).add(messageObserver);
        sendMessage(new Message(channelName, MessageType.SUBSCRIBE));

    }

    public void openConnection() {
        System.out.println("Establishing connection.");
        while (socket == null) {
            try {
                InetAddress ip = InetAddress.getByName(server);
                Socket socket = new Socket(ip, port);
                System.out.println("Connection established on " + server + ":" + port);
                this.socket = socket;

                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                executorService.submit(this);

            } catch (UnknownHostException e) {
                System.out.println("The server is unknown.");
            } catch (IOException e) {
                System.out.println("Server is offline.");
            }
        }
    }

    public void createChannel(String channelName) {
        Message channelRequest = new Message();
        channelRequest.setType(MessageType.CREATE_CHANNEL);
        channelRequest.setChannelName(channelName);
        sendMessage(channelRequest);
    }

    public void sendMessage(Message message) {
        String serializedMessage;
        try {
            serializedMessage = JsonConverter.convertToJson(message);
            output.println(serializedMessage);
            output.println();
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
