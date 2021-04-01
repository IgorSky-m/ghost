package com.skachko.sender.support.converters;


import com.skachko.sender.api.IGhostRequest;
import com.skachko.sender.api.IGhostResponse;
import com.skachko.sender.support.converters.api.IConverter;
import com.skachko.sender.essences.request.GhostRequest;
import com.skachko.sender.essences.response.GhostResponse;
import com.skachko.sender.support.exceptions.ConverterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;


public class Converter implements IConverter {

    private final static Logger logger = LoggerFactory.getLogger(Converter.class);


    private final ObjectMapper objectMapper;

    public Converter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public List<IGhostRequest> stringToRequest(List<String> request) {
        return request.stream()
                .map(e -> {
                    try {
                        return objectMapper.readValue(e, GhostRequest.class);
                    } catch (JsonProcessingException err) {
                        String errMsg = String.format("error convert request. cause by %s", err.getClass().getName());
                        logger.error(errMsg,err);
                        throw new ConverterException(errMsg,err);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public IGhostRequest singleValueToRequest(String request){
        try {
            return objectMapper.readValue(request, GhostRequest.class);
        } catch (JsonProcessingException err) {
            String errMsg = String.format("error convert request. cause by %s", err.getClass().getName());
            logger.error(errMsg,err);
            throw new ConverterException(errMsg,err);
        }
    }

    @Override
    public String singleRequestToString(IGhostRequest request){
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException err) {
            String errMsg = String.format("error convert request. cause by %s", err.getClass().getName());
            logger.error(errMsg,err);
            throw new ConverterException(errMsg,err);
        }
    }


    @Override
    public List<String> requestToString(List<IGhostRequest> requests) {
        return requests.stream()
                .map(e -> {
                    try {
                        return this.objectMapper.writeValueAsString(e);
                    } catch (JsonProcessingException err) {
                        String errMsg = String.format("error convert request. cause by %s", err.getClass().getName());
                        logger.error(errMsg,err);
                        throw new ConverterException(errMsg,err);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<IGhostResponse> stringToResponse(List<String> responses) {
        return responses.stream()
                .map(e -> {
                    try {
                        return objectMapper.readValue(e, GhostResponse.class);
                    } catch (JsonProcessingException err) {
                        String errMsg = String.format("error convert response. cause by %s", err.getClass().getName());
                        logger.error(errMsg,err);
                        throw new ConverterException(errMsg,err);
                    }
                })
                .collect(Collectors.toList());
    }



    @Override
    public IGhostResponse singleResponseToString(String response){
        try {
            return objectMapper.readValue(response, GhostResponse.class);
        } catch (JsonProcessingException err) {
            String errMsg = String.format("error convert response. cause by %s", err.getClass().getName());
            logger.error(errMsg,err);
            throw new ConverterException(errMsg,err);
        }
    }

    @Override
    public String singleResponseToString(IGhostResponse response){
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException err) {
            String errMsg = String.format("error convert response. cause by %s", err.getClass().getName());
            logger.error(errMsg,err);
            throw new ConverterException(errMsg,err);
        }
    }

    @Override
    public List<String> responseToString(List<GhostResponse> responses) {
        return responses.stream()
                .map(e -> {
                    try {
                        return this.objectMapper.writeValueAsString(e);
                    } catch (JsonProcessingException err) {
                        String errMsg = String.format("error convert response. cause by %s", err.getClass().getName());
                        logger.error(errMsg,err);
                        throw new ConverterException(errMsg,err);
                    }
                })
                .collect(Collectors.toList());
    }


}
