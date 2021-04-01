package com.skachko.receiver.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnProperty(value = "ghost.receiver.enabled", havingValue = "true")
public class QueueConfig {

    @Value("${spring.application.name}")
    private String senderName;

    @Bean
    public Queue queue(){
        return new Queue(String.format("%s.%s",this.senderName,"request"),false);
    }

}
