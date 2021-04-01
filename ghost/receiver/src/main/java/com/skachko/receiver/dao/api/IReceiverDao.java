package com.skachko.receiver.dao.api;

import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.essences.transfer.RequestProperties;

import java.util.List;
import java.util.Map;

public interface IReceiverDao {
    IGhostResponse read(RequestProperties properties);
    List<IGhostResponse> readAll(Map.Entry<String, List<RequestProperties>> entry);

}
