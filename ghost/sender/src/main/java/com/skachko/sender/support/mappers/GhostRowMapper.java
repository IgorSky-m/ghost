package com.skachko.sender.support.mappers;

import com.skachko.sender.essences.ghost.dao.api.IGhostDao;
import com.skachko.sender.essences.ghost.dto.Ghost;
import com.skachko.sender.api.IGhost;
import net.nvcm.sugar.dao.tricks.mapper.EssenceCustomRowMapper;
import net.nvcm.sugar.dao.tricks.mapper.RowMapperUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GhostRowMapper extends EssenceCustomRowMapper<IGhost> {

    private final IGhostDao dao;

    public GhostRowMapper(IGhostDao dao) {
        this.dao = dao;
    }

    @Override
    public IGhost mapRow(String tableName, ResultSet rs, int rowNum) throws SQLException {
        IGhost ghost = new Ghost();
        this.mapRow(tableName,rs,rowNum,ghost);
        ghost.setBeanName(RowMapperUtils.getValue(rs,tableName,IGhost.BEAN_NAME,this.dao.getFields()));
        ghost.setFieldType(RowMapperUtils.getValue(rs,tableName,IGhost.FIELD_TYPE,this.dao.getFields()));
        ghost.setFieldName(RowMapperUtils.getValue(rs,tableName,IGhost.FIELD_NAME,this.dao.getFields()));
        ghost.setSearchValue(RowMapperUtils.getValue(rs,tableName,IGhost.SEARCH_VALUE,this.dao.getFields()));
        ghost.setLastSend(RowMapperUtils.getValue(rs,tableName,IGhost.DT_LAST_SEND,this.dao.getFields()));
        ghost.setResponseStatus(RowMapperUtils.getValue(rs,tableName,IGhost.RESPONSE_STATUS,this.dao.getFields()));
        ghost.setResponseUuid(RowMapperUtils.getValue(rs,tableName,IGhost.RESPONSE_UUID,this.dao.getFields()));
        return ghost;
    }

    @Override
    public IGhost mapRow(ResultSet resultSet, int i) throws SQLException {
        return this.mapRow(this.dao.getTableName(),resultSet,i);
    }
}
