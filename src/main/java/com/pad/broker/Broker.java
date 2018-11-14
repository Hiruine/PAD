package com.pad.broker;

import com.pad.components.Subscriber;
import com.pad.components.Message;
import com.pad.components.Topic;

import java.util.Set;

public interface Broker {

    void publish(Message message, Topic topic);

    boolean subscribe(Subscriber subscriber, Topic topic);

    boolean unsubscribe(Subscriber subscriber, Topic topic);

    Set<Topic> getTopics();

    Set<Subscriber> getSubscribers();
}
