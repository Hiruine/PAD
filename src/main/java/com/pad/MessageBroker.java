package com.pad;

import com.pad.dto.Message;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBroker {

    private Map<String, BlockingQueue<Message>> queues = new ConcurrentHashMap<>();


    public MessageBroker() {
    }

    public void createChannel(String channelName) {
        queues.put(channelName, new LinkedBlockingQueue<>());
    }

    public void deleteChannel(String channelName) {
        queues.remove(channelName);
    }


    public void addMessage(Message message) throws InterruptedException {
        BlockingQueue<Message> queue = queues.get(message.getChannelName());
        if (queue != null) {
            queue.put(message);
        } else {
            throw new RuntimeException("Channel [" + message.getChannelName() + "] does not exist.");
        }
    }

    public Message getMessage(String channelName) throws InterruptedException {
        BlockingQueue<Message> queue = queues.get(channelName);
        if (queue != null) {
            return queue.take();
        } else {
            throw new RuntimeException("Channel [" + channelName + "] does not exist.");
        }
    }

    public void deleteMessage(Message msg) throws InterruptedException {
        BlockingQueue<Message> queue = queues.get(msg.getChannelName());
        queue.remove(msg);
    }

    public boolean hasMessages(String queueName) throws InterruptedException {
        BlockingQueue<Message> queue = queues.get(queueName);
        return !queue.isEmpty();
    }
}
