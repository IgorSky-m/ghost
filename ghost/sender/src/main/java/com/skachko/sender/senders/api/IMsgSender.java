package com.skachko.sender.senders.api;

import com.skachko.sender.enums.MessageType;

import java.util.List;

public interface IMsgSender {
    void sendMsg(String receiverName, MessageType messageType, List<String> msgList);
}
