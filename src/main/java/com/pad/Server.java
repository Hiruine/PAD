package com.pad;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty())
            this.serverSocket = new ServerSocket(81, 1, InetAddress.getByName(ipAddress));
        else
            this.serverSocket = new ServerSocket(81, 1, InetAddress.getLocalHost());
    }

    public void listen() throws Exception {

        while(true) {
            Socket clientSocket = this.serverSocket.accept();

            new ServerThread(clientSocket).start();
        }




    }








}
