package com.skachko.receiver.essences.request;

import com.skachko.receiver.api.IGhostRequest;
import com.skachko.receiver.enums.FieldType;

import java.util.UUID;

public class GhostRequest implements IGhostRequest {

    private UUID uuid;
    private String beanName;
    private String fieldName;
    private FieldType fieldType;
    private String value;
    private String senderName;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID requestUuid) {
        this.uuid = requestUuid;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String getSenderName() {
        return senderName;
    }

    @Override
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
