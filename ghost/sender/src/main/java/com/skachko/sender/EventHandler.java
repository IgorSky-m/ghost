package com.skachko.sender;

import com.skachko.sender.api.IGhostRequest;
import com.skachko.sender.config.GhostProperties;
import com.skachko.sender.enums.MessageType;
import com.skachko.sender.essences.ghost.services.api.IGhostService;
import com.skachko.sender.senders.api.IMsgSender;
import com.skachko.sender.support.converters.api.IConverter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class EventHandler implements Job {

    @Autowired
    private  IGhostService service;

    @Autowired
    private  GhostProperties properties;

    @Autowired
    private  IMsgSender msgSender;

    @Autowired
    private  IConverter converter;

    @Value("${spring.application.name}")
    private  String senderName;

    public EventHandler(){
    }

    @Override
    public void execute(JobExecutionContext context) {
        Map<String,List<String>> requestMap = new HashMap<>();
        int ttl = properties.getTtl() == 0 ? 30000: properties.getTtl();
        int batch = properties.getBatch() == 0 ? 1000: properties.getBatch();

        List<IGhostRequest> requests = this.service.extractUuidsOfDirtyShadows(ttl, batch);

        if (requests.size() > 0) {
            for (IGhostRequest request : requests) {
                request.setSenderName(this.senderName);
                String requestStringRepresentation = this.converter.singleRequestToString(request);
                String beanName = request.getBeanName().toLowerCase(Locale.ROOT);
                if (requestMap.containsKey(beanName)) {
                    requestMap.get(beanName).add(requestStringRepresentation);
                } else {
                    List<String> req = new ArrayList<>();
                    req.add(requestStringRepresentation);
                    requestMap.put(beanName, req);
                }
            }

            requestMap.forEach((k, v) -> this.msgSender.sendMsg(this.properties.getReceivers().get(k), MessageType.REQUEST, v));
        }

    }
}
