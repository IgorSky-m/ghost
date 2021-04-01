package com.skachko.receiver.api;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.skachko.receiver.enums.FieldType;
import com.skachko.receiver.essences.request.GhostRequest;

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