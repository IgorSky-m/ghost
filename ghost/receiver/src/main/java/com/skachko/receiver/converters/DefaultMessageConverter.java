package com.skachko.receiver.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.receiver.converters.api.IValueConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.skachko.receiver.api.IGhostRequest;
import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.converters.api.IConverter;
import com.skachko.receiver.enums.FieldType;
import com.skachko.receiver.essences.request.GhostRequest;
import com.skachko.receiver.essences.response.GhostResponse;
import com.skachko.receiver.exceptions.ConverterException;

import java.util.List;
import java.util.stream.Collectors;


public class DefaultMessageConverter implements IConverter {

    private final static Logger logger = LoggerFactory.getLogger(DefaultMessageConverter.class);


    private final ObjectMapper objectMapper;
    private final IValueConverter valueConverter;

    public DefaultMessageConverter(ObjectMapper objectMapper, IValueConverter valueConverter) {
        this.objectMapper = objectMapper;
        this.valueConverter = valueConverter;
    }

    public DefaultMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.valueConverter = new DefaultValueConverter();
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
    public List<String> responseToString(List<IGhostResponse> responses) {
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

    public Object convertValueFromString(String value, FieldType fieldType){
        switch (fieldType){
            case INTEGER:
            case INTEGER_PRIMITIVE: return valueConverter.toInteger(value);
            case UUID: return valueConverter.tiUuid(value);
            case STRING: return valueConverter.toString(value);
            case CHARACTER:
            case CHARACTER_PRIMITIVE: return valueConverter.toChar(value);
            case DECIMAL: return valueConverter.toDecimal(value);
            case LONG:
            case LONG_PRIMITIVE: return valueConverter.toLong(value);
            case DOUBLE:
            case DOUBLE_PRIMITIVE: return valueConverter.toDouble(value);
            default: return value;

        }
    }
}
