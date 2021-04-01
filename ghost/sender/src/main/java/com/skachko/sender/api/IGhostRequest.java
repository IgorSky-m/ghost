package com.skachko.sender.api;

import com.skachko.sender.enums.FieldType;
import com.skachko.sender.essences.request.GhostRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;

@JsonDeserialize(as = GhostRequest.class)
public interface IGhostRequest {

    UUID getUuid();
    void setUuid(UUID uuid);

    String getBeanName();
    void setBeanName(String beanName);


    String getFieldName();
    void setFieldName(String fieldName);

    String getValue();
    void setValue(String value);

    FieldType getFieldType();
    void setFieldType(FieldType fieldType);

    String getSenderName();
    void setSenderName(String senderName);

}
