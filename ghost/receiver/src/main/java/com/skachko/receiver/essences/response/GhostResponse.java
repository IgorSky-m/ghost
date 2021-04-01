package com.skachko.receiver.essences.response;


import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.enums.GhostStatus;

import java.util.UUID;

public class GhostResponse implements IGhostResponse {

    private UUID requestUuid;
    private String value;

    private UUID responseBeanUuid;
    private GhostStatus status;
    private String beanName;
    private String fieldName;

    public UUID getRequestUuid() {
        return requestUuid;
    }

    public void setRequestUuid(UUID requestUuid) {
        this.requestUuid = requestUuid;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public UUID getResponseBeanUuid() {
        return responseBeanUuid;
    }

    public void setResponseBeanUuid(UUID responseBeanUuid) {
        this.responseBeanUuid = responseBeanUuid;
    }

    public GhostStatus getStatus() {
        return status;
    }

    public void setStatus(GhostStatus status) {
        this.status = status;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
