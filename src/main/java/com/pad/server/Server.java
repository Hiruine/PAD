package com.pad.server;

import com.pad.broker.MessageBroker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(11);
    private MessageBroker messageBroker = new MessageBroker();

    public Server(int port) {
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(this.port);

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void listen() {
        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

//                this.executorService.execute(new ClientHandler(this, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
