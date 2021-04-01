package com.skachko.receiver.dao;

import net.nvcm.sugar.dao.tricks.api.IReadRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.config.GhostProperties;
import com.skachko.receiver.converters.api.IConverter;
import com.skachko.receiver.dao.api.IReceiverDao;
import com.skachko.receiver.essences.transfer.RequestProperties;
import com.skachko.receiver.support.mappers.ReceiverRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ReceiverDao implements IReceiverDao {

    private static final String SQL_PATTERN_ONE = "SELECT %s.%s, %s.%s FROM %s.%s WHERE %s.%s = ?";

    private final ApplicationContext context;
    private final JdbcTemplate jdbcTemplate;
    private final IConverter converter;
    private final GhostProperties properties;
    private final ReceiverRowMapper receiverRowMapper;

    public ReceiverDao(ApplicationContext context, JdbcTemplate jdbcTemplate, IConverter converter, GhostProperties properties) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
        this.converter = converter;
        this.properties = properties;
        this.receiverRowMapper = new ReceiverRowMapper();
    }

    public IGhostResponse read(RequestProperties properties){
        String sql = String.format(
                SQL_PATTERN_ONE,
                properties.getReadRepository().getTableName(),"uuid",
                properties.getReadRepository().getTableName(),properties.getRequest().getFieldName(),
                properties.getReadRepository().getSchemaName(),properties.getReadRepository().getTableName(),
                properties.getReadRepository().getTableName(),properties.getRequest().getFieldName()
                );

        return this.jdbcTemplate
                .query(
                        sql,
                        new ReceiverRowMapper(),
                        this.converter.convertValueFromString(
                                properties.getRequest().getValue(),
                                properties.getRequest().getFieldType()
                        ))
                .stream()
                .findFirst()
                .orElse(null);
    }



    public List<IGhostResponse> readAll(Map.Entry<String, List<RequestProperties>> entry) {
        IReadRepository beanDao =
                (IReadRepository) context.getBean(properties.getEssences().get(entry.getKey()).get("dao"));

        MapSqlParameterSource param = new MapSqlParameterSource();
        StringBuilder builder = new StringBuilder("SELECT ");
        builder.append(beanDao.getTableName()).append(".").append("uuid");
        if (entry.getValue().size() > 0) {
            List<Object> propsList = new ArrayList<>();
            builder
                    .append(", ")
                    .append(beanDao.getTableName()).append(".").append(entry.getValue().get(0).getRequest().getFieldName())
                    .append(" FROM ")
                    .append(beanDao.getSchemaName()).append(".").append(beanDao.getTableName())
                    .append(" WHERE ")
                    .append(beanDao.getTableName()).append(".").append(entry.getValue().get(0).getRequest().getFieldName())
                    .append(" IN ").append("(");

            for (int i = 0; i < entry.getValue().size() ;) {
                builder

                        .append(" ? ");
                propsList.add(this.converter.convertValueFromString(
                        entry.getValue().get(i).getRequest().getValue(),
                        entry.getValue().get(i).getRequest().getFieldType()
                ));
                i++;
                if (i < entry.getValue().size()) {
                    builder.append(", ");
                }

            }

            builder.append(");");

            return this.jdbcTemplate.query(builder.toString(), propsList.toArray(), this.receiverRowMapper);
        }

        return null;
    }
}
