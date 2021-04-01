package com.skachko.receiver.support.mappers;

import org.springframework.jdbc.core.RowMapper;
import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.essences.response.GhostResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ReceiverRowMapper implements RowMapper<IGhostResponse> {

    @Override
    public IGhostResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        IGhostResponse response = new GhostResponse();
        response.setResponseBeanUuid(UUID.fromString(rs.getString(1)));
        response.setValue(rs.getString(2));
        return response;
    }
}
