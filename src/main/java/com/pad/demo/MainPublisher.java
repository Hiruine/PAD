package com.pad.demo;

import com.pad.Client.Client;
import com.pad.dto.Message;
import com.pad.dto.MessageType;
import com.pad.dto.converter.GsonConverter;
import com.pad.dto.entity.Student;

public class MainPublisher {

    public static void main(String[] args) {

        Client client = new Client("localhost", 1234);
        client.openConnection();
        client.createChannel("PAD");

        Message message = new Message();
        message.setData(GsonConverter.convertToJson(new Student("Radu", "TI-153", 22)));
        message.setType(MessageType.SEND_MESSAGE);
        message.setChannelName("PAD");

    }
}
