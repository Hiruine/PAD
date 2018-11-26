package com.pad.broker;

import com.pad.dto.Message;
import com.pad.network.ClientConnection;

import java.util.ArrayList;
import java.util.List;

public class MessageChannel implements Channel{

    private final String channelName;
    private final List<ClientConnection> clients = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();

    public MessageChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public String getChannelName() {
        return this.channelName;
    }

    @Override
    public List<ClientConnection> getClients() {
        return this.clients;
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public void subscribeClient(ClientConnection clientConnection) {
        clients.add(clientConnection);
    }

    @Override
    public void unsubscribeClient(ClientConnection clientConnection) {
        clients.remove(clientConnection);
    }
}
