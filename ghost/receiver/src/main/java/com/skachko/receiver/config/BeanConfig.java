package com.skachko.receiver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.receiver.converters.DefaultMessageConverter;
import com.skachko.receiver.converters.api.IConverter;
import com.skachko.receiver.dao.ReceiverDao;
import com.skachko.receiver.dao.api.IReceiverDao;
import com.skachko.receiver.handlers.ResponseHandler;
import com.skachko.receiver.handlers.api.IResponseHandler;
import com.skachko.receiver.listeners.MsgListener;
import com.skachko.receiver.listeners.api.IMsgListener;
import com.skachko.receiver.senders.ResponseSender;
import com.skachko.receiver.senders.api.IMsgSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableConfigurationProperties({GhostProperties.class})
@ConditionalOnProperty(value = "ghost.receiver.enabled", havingValue = "true")
public class BeanConfig {

    @Autowired
    private GhostProperties properties;

    @Bean
    public IConverter converter(ObjectMapper objectMapper){
        return new DefaultMessageConverter(objectMapper);
    }

    @Bean
    public IReceiverDao dao(ApplicationContext ctx, JdbcTemplate jdbcTemplate, IConverter converter){
        return new ReceiverDao(
                ctx,
                jdbcTemplate,
                converter,
                this.properties
        );
    }

    @Bean
    public IResponseHandler handler (ApplicationContext ctx, IConverter converter, IReceiverDao dao, IMsgSender sender){
        return new ResponseHandler(
                ctx,
                this.properties,
                converter,
                dao,
                sender
        );
    }

    @Bean
    public IMsgListener listener(IResponseHandler handler) {
        return new MsgListener(handler);
    }

    @Bean
    public IMsgSender responseSender(RabbitTemplate rabbitTemplate) {
        return new ResponseSender(rabbitTemplate);
    }




}
