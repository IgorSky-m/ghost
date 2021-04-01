package com.skachko.sender.senders;

import com.skachko.sender.enums.MessageType;
import com.skachko.sender.senders.api.IMsgSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;


public class MsgSender implements IMsgSender {

    private static final Logger logger = LoggerFactory.getLogger(MsgSender.class);

    private final RabbitTemplate rabbitTemplate;

    public MsgSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsg(String receiverName, MessageType messageType, List<String> msgList) {
        String recName = String.format("%s.%s",receiverName,messageType.suffix);
        this.rabbitTemplate.convertAndSend(recName,msgList);
        logger.info(String.format("send %d search value%s to %s", msgList.size(),msgList.size() == 1 ? "":"s",recName));
    }
}
