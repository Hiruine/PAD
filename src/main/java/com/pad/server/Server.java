package com.pad.server;

import com.pad.ClientHandler;
import com.pad.MessageBroker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(11);
    private MessageBroker messageBroker = new MessageBroker();

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void listen() {
        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                this.executorService.execute(new ClientHandler(messageBroker, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
