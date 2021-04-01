package com.skachko.sender.api;

import com.skachko.sender.enums.GhostStatus;
import com.skachko.sender.enums.FieldType;
import com.skachko.sender.essences.ghost.dto.Ghost;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.nvcm.sugar.core.dto.api.IEssence;

import java.util.Date;
import java.util.UUID;

@JsonDeserialize(as = Ghost.class)
public interface IGhost extends IEssence {
    String BEAN_NAME =  "bean_name";
    String FIELD_NAME = "field_name";
    String DT_LAST_SEND = "dt_last_send";
    String FIELD_TYPE = "field_type";
    String SEARCH_VALUE = "search_value";
    String RESPONSE_UUID = "response_uuid";
    String RESPONSE_STATUS = "response_status";


    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Override
    Date getDtCreate();
    @Override
    void setDtCreate(Date dtCreate);

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Override
    Date getDtUpdate();
    @Override
    void setDtUpdate(Date dtUpdate);

    String getBeanName();

    void setBeanName(String beanName);

    String getFieldName();

    void setFieldName(String fieldName);

    Date getLastSend();

    void setLastSend(Date lastSend);

    FieldType getFieldType();

    void setFieldType(FieldType fieldType);

    String getSearchValue();

    void setSearchValue(String searchValue);

    UUID getResponseUuid();

    void setResponseUuid(UUID responseUuid);

    GhostStatus getResponseStatus();

    void setResponseStatus(GhostStatus responseStatus);


}
