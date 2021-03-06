package com.pad.dto;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 5417324113581673181L;

    private String data;
    private String channelName;
    private MessageType type;
    private String status;

    public Message() {
    }

    public Message(String channelName, MessageType type) {
        this.channelName = channelName;
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
