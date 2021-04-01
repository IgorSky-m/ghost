package com.skachko.sender.listeners;


import com.skachko.sender.handlers.api.IResponseHandler;
import com.skachko.sender.listeners.api.IMsgListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.List;


public class ResponseListener implements IMsgListener {


    private final IResponseHandler handler;

    public ResponseListener(IResponseHandler handler){
        this.handler = handler;
    }

    @RabbitListener(queues = "${spring.application.name}.response")
    public void listen(List<String> msgList) {
        this.handler.handle(msgList);
    }
}
