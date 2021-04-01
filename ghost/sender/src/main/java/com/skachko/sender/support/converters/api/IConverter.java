package com.skachko.sender.support.converters.api;

import com.skachko.sender.api.IGhostRequest;
import com.skachko.sender.api.IGhostResponse;
import com.skachko.sender.essences.response.GhostResponse;

import java.util.List;

public interface IConverter {
    List<IGhostRequest> stringToRequest(List<String> request);
    List<String> responseToString(List<GhostResponse> responses);
    List<String> requestToString(List<IGhostRequest> requests);
    List<IGhostResponse> stringToResponse(List<String> responses);
    IGhostRequest singleValueToRequest(String request);
    IGhostResponse singleResponseToString(String response);
    String singleResponseToString(IGhostResponse response);
    String singleRequestToString(IGhostRequest request);
}
