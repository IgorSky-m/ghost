package com.skachko.sender.essences.ghost.dto;

import com.skachko.sender.enums.GhostStatus;
import com.skachko.sender.enums.FieldType;
import com.skachko.sender.api.IGhost;
import net.nvcm.sugar.core.dto.api.AEssence;

import java.util.Date;
import java.util.UUID;

public class Ghost extends AEssence implements IGhost {


    private String beanName;
    private String columnName;
    private Date lastSend;
    private FieldType columnType;
    private String searchValue;
    private UUID responseUuid;
    private GhostStatus responseStatus;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getFieldName() {
        return columnName;
    }

    public void setFieldName(String fieldName) {
        this.columnName = fieldName;
    }

    public Date getLastSend() {
        return lastSend;
    }

    public void setLastSend(Date lastSend) {
        this.lastSend = lastSend;
    }

    public FieldType getFieldType() {
        return columnType;
    }

    public void setFieldType(FieldType columnType) {
        this.columnType = columnType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public UUID getResponseUuid() {
        return responseUuid;
    }

    public void setResponseUuid(UUID responseUuid) {
        this.responseUuid = responseUuid;
    }

    public GhostStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(GhostStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
