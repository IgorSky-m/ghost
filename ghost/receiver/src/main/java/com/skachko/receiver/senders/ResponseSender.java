package com.skachko.receiver.senders;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.skachko.receiver.enums.MessageType;
import com.skachko.receiver.senders.api.IMsgSender;

import java.util.List;


public class ResponseSender implements IMsgSender {

    private final RabbitTemplate rabbitTemplate;

    public ResponseSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsg(String receiverName, MessageType messageType, List<String> msgList) {
        String responseName = String.format("%s.%s", receiverName,messageType.suffix);
        this.rabbitTemplate.convertAndSend(responseName ,msgList);
    }
}
