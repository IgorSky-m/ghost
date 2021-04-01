package com.skachko.sender.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.sender.api.IGhost;
import com.skachko.sender.api.IGhostResponse;
import com.skachko.sender.config.GhostProperties;
import com.skachko.sender.essences.ghost.services.api.IGhostService;
import com.skachko.sender.essences.response.GhostResponse;
import com.skachko.sender.handlers.api.IResponseHandler;
import net.nvcm.sugar.search.core.SearchFactory;
import net.nvcm.sugar.search.core.api.ComparisonOperator;
import net.nvcm.sugar.search.core.api.ISearchFactory;
import net.nvcm.sugar.search.core.api.ISearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;
import java.util.stream.Collectors;


public class ResponseHandler implements IResponseHandler, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private final ObjectMapper objectMapper;


    private final GhostProperties properties;

    private final IGhostService service;

    private final ISearchFactory sf = SearchFactory.getInstance();

    public ResponseHandler(ObjectMapper objectMapper,GhostProperties properties,IGhostService service){
        this.objectMapper = objectMapper;
        this.properties = properties;
        this.service = service;
    }

    private ApplicationContext ctx;

    @Override
    public List<IGhostResponse> handle(List<String> msgList) {
        List<UUID> requestUuids = new ArrayList<>();
        List<IGhostResponse> responses = msgList.stream()
                .peek(logger::info)
                .map(e -> {
                    try {
                        return objectMapper.readValue(e, new TypeReference<GhostResponse>() {
                        });
                    } catch (JsonProcessingException jsonProcessingException) {
                        logger.error(jsonProcessingException.getMessage(),jsonProcessingException);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .peek(e -> requestUuids.add(e.getRequestUuid()))
                .collect(Collectors.toList());

        ISearchQuery query = this.sf.createQuery();
        query.setSearchExpression(this.sf.or().add(
                this.sf.sUnit(IGhost.ESSENCE_KEY, ComparisonOperator.IN, requestUuids)
        ));

        List<IGhost> iGhosts = this.service.get(query);
        Map<UUID, IGhost> ghostMap = iGhosts.stream().collect(Collectors.toMap(IGhost::getUuid,ghost -> ghost));

        for (IGhostResponse response : responses) {
            IGhost ghost = ghostMap.get(response.getRequestUuid());
            ghost.setResponseStatus(response.getStatus());
            ghost.setResponseUuid(response.getResponseBeanUuid());
            this.service.update(ghost.getUuid(),ghost.getDtUpdate(),ghost);
        }

        return responses;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
