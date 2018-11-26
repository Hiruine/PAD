package com.pad.demo;

import com.pad.server.Server;

public class MainBroker {

    private static final int PORT = 1234;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.listen();
    }
}
