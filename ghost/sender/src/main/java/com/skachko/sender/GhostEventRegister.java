package com.skachko.sender;

import com.skachko.sender.enums.FieldType;
import com.skachko.sender.enums.GhostStatus;
import com.skachko.sender.essences.ghost.dao.api.IGhostDao;
import com.skachko.sender.essences.ghost.dto.Ghost;
import com.skachko.sender.api.IGhost;
import com.skachko.sender.api.IGhostEventRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


public class GhostEventRegister implements IGhostEventRegister {


    private final IGhostDao dao;


    public GhostEventRegister(IGhostDao dao) {
        this.dao = dao;
    }

    private static final Logger logger = LoggerFactory.getLogger(GhostEventRegister.class);

    @Override
    public void register(Object value, String fieldName, String dtoBeanName) {

        Assert.notNull(value, "value can't be null");
        Assert.notNull(fieldName, "fieldName can't be null");
        Assert.notNull(dtoBeanName, "dtoBeanName can't be null");

        IGhost ghost = new Ghost();
        ghost.setFieldName(fieldName);
        ghost.setSearchValue(this.valueToString(value));
        ghost.setFieldType(this.getFieldType(value));
        ghost.setBeanName(dtoBeanName);
        ghost.setResponseStatus(GhostStatus.WAIT);

        this.dao.create(ghost);

    }


    private FieldType getFieldType(Object value){
        return FieldType.findByName(value.getClass().getSimpleName());
    }

    private String valueToString(Object value) {
        return String.valueOf(value);
    }

}
