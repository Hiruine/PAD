package com.pad.Client;

import com.pad.dto.Message;

public interface MessageObserver {
    void consumeMessage(Message message);
}
