package com.skachko.sender.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.sender.EventHandler;
import com.skachko.sender.GhostEventRegister;
import com.skachko.sender.api.IGhostEventRegister;
import com.skachko.sender.config.quartz.AutowiringSpringBeanJobFactory;
import com.skachko.sender.essences.ghost.dao.GhostDao;
import com.skachko.sender.essences.ghost.dao.api.IGhostDao;
import com.skachko.sender.essences.ghost.services.GhostService;
import com.skachko.sender.essences.ghost.services.api.IGhostService;
import com.skachko.sender.handlers.ResponseHandler;
import com.skachko.sender.handlers.api.IResponseHandler;
import com.skachko.sender.listeners.ResponseListener;
import com.skachko.sender.listeners.api.IMsgListener;
import com.skachko.sender.senders.MsgSender;
import com.skachko.sender.senders.api.IMsgSender;
import com.skachko.sender.support.converters.Converter;
import com.skachko.sender.support.converters.api.IConverter;
import net.nvcm.sugar.search.core.SearchFactory;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
@ConditionalOnProperty(value = "ghost.sender.enabled", havingValue = "true")
@EnableConfigurationProperties({GhostProperties.class})
public class BeanConfig {


    private final GhostProperties properties;

    private final ApplicationContext ctx;

    public BeanConfig(ApplicationContext context, GhostProperties properties) {
        this.ctx = context;
        this.properties = properties;
    }

    @Bean
    public IGhostDao dao (@Value("${ghost.sender.database.schema}")String schema, @Value("${ghost.sender.database.table}") String table) {
        return new GhostDao(schema,table, SearchFactory.createNewFactory());
    }

    @Bean
    public IConverter converter(ObjectMapper objectMapper){
        return new Converter(objectMapper);
    }

    @Bean
    public IMsgSender sender(RabbitTemplate rabbitTemplate){
        return new MsgSender(rabbitTemplate);
    }

    @Bean
    public IGhostEventRegister register(IGhostDao dao){
        return new GhostEventRegister(dao);
    }

    @Bean
    public IGhostService service(IGhostDao dao) {
        return new GhostService(dao);
    }

    @Bean(name = "responseHandler")
    public IResponseHandler handler(ObjectMapper objectMapper, IGhostService service){
        return new ResponseHandler(objectMapper,this.properties,service);
    }

    @Bean
    public IMsgListener responseListener(@Qualifier("responseHandler") IResponseHandler handler) {
        return new ResponseListener(handler);
    }




}
