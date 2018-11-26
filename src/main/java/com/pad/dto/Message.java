package com.pad.dto;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 5417324113581673181L;

    private String id;
    private String data;
    private String channelName;
    private MessageType type;

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
