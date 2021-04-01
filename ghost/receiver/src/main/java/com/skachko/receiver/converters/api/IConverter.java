package com.skachko.receiver.converters.api;

import com.skachko.receiver.api.IGhostRequest;
import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.enums.FieldType;

import java.util.List;

public interface IConverter {
    List<IGhostRequest> stringToRequest(List<String> request);
    List<String> responseToString(List<IGhostResponse> responses);
    List<String> requestToString(List<IGhostRequest> requests);
    List<IGhostResponse> stringToResponse(List<String> responses);
    IGhostRequest singleValueToRequest(String request);
    IGhostResponse singleResponseToString(String response);
    String singleResponseToString(IGhostResponse response);
    String singleRequestToString(IGhostRequest request);
    Object convertValueFromString(String value, FieldType fieldType);
}
