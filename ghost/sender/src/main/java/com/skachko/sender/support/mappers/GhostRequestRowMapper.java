package com.skachko.sender.support.mappers;

import com.skachko.sender.api.IGhostRequest;
import com.skachko.sender.enums.FieldType;
import com.skachko.sender.essences.request.GhostRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GhostRequestRowMapper implements RowMapper<IGhostRequest> {

    @Override
    public IGhostRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        IGhostRequest request = new GhostRequest();
        request.setUuid(UUID.fromString(rs.getString(1)));
        request.setBeanName(rs.getString(2));
        request.setFieldName(rs.getString(3));
        request.setFieldType(FieldType.valueOf(rs.getString(4)));
        request.setValue(rs.getString(5));
        return request;
    }
}
