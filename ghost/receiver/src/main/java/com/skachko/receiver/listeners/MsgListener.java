package com.skachko.receiver.listeners;


import com.skachko.receiver.handlers.api.IResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.handlers.ResponseHandler;
import com.skachko.receiver.listeners.api.IMsgListener;

import java.util.List;

public class MsgListener implements IMsgListener {

    private final static Logger logger = LoggerFactory.getLogger(MsgListener.class);

    private final IResponseHandler handler;

    public MsgListener( IResponseHandler handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "${spring.application.name}.request")
    public void listen(List<String> request) {
            List<IGhostResponse> list = this.handler.handle(request);
    }

}
