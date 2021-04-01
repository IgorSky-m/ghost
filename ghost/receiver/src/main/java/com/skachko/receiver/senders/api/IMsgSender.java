package com.skachko.receiver.senders.api;

import com.skachko.receiver.enums.MessageType;

import java.util.List;

public interface IMsgSender {
    void sendMsg(String receiverName, MessageType messageType, List<String> msgList);
}
