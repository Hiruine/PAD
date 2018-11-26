package com.pad.demo;

import com.pad.Client.Client;
import com.pad.dto.converter.GsonConverter;
import com.pad.dto.entity.Student;

public class MainReceiver {

    public static void main(String[] args) {

        Client client = new Client("localhost", 1234);
        client.openConnection();
        client.subscribe("PAD", message -> {
            Student student = GsonConverter.convertToDto(message.getData(), Student.class);
            System.out.println(student);
        });
    }
}
