package com.skachko.sender.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "ghost.sender.enabled", havingValue = "true")
public class QueueConfig  {

    @Value("${spring.application.name}")
    private String senderName;


    @Bean
    public Queue response(){
        return new Queue(String.format("%s.%s",this.senderName,"response"), false);
    }

}
