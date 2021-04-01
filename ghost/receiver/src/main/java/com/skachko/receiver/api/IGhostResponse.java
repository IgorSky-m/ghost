package com.skachko.receiver.api;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.skachko.receiver.enums.GhostStatus;
import com.skachko.receiver.essences.response.GhostResponse;

import java.io.Serializable;
import java.util.UUID;

@JsonDeserialize(as = GhostResponse.class)
public interface IGhostResponse extends Serializable {

    UUID getRequestUuid();
    void setRequestUuid(UUID requestUuid);

    String getValue();
    void setValue(String value);

    UUID getResponseBeanUuid();
    void setResponseBeanUuid(UUID responseBeanUuid);

    GhostStatus getStatus();
    void setStatus(GhostStatus status);

    String getBeanName();
    void setBeanName(String beanName);

    String getFieldName();
    void setFieldName(String fieldName);

}
