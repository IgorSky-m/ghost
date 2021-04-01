package com.skachko.sender.essences.ghost.dao;

import com.skachko.sender.api.IGhostRequest;
import com.skachko.sender.enums.GhostStatus;
import com.skachko.sender.enums.FieldType;
import com.skachko.sender.essences.ghost.dao.api.IGhostDao;
import com.skachko.sender.api.IGhost;
import com.skachko.sender.support.mappers.GhostRequestRowMapper;
import com.skachko.sender.support.mappers.GhostRowMapper;
import net.nvcm.sugar.core.dto.api.IEssence;
import net.nvcm.sugar.dao.tricks.api.CRUDRepository;
import net.nvcm.sugar.dao.tricks.api.Column;
import net.nvcm.sugar.dao.tricks.api.IColumn;
import net.nvcm.sugar.dao.tricks.api.PGColumnType;
import net.nvcm.sugar.search.core.api.ISearchFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class GhostDao extends CRUDRepository<IGhost,IGhost> implements IGhostDao {

    private static final String SET_DT_LAST_TRY_INV_SQL_PATTERN =
            "UPDATE %s.%s SET dt_last_send = ? " +
                    "WHERE uuid in " +
                    "(SELECT uuid FROM %s.%s WHERE %s.response_status = 'WAIT' AND (dt_last_send IS NULL OR dt_last_send <= ('%s'::timestamp - interval '%d milliseconds'))  ORDER BY dt_update, dt_last_send NULLS FIRST LIMIT %d) " +
                    "RETURNING %s, %s, %s, %s, %s";





    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");

    private static final Logger logger = LoggerFactory.getLogger(GhostDao.class);


    public GhostDao(String schemaName, String tableName, ISearchFactory sf) {
        super(schemaName, tableName, sf);
        this.setBriefMapper(new GhostRowMapper(this));
        this.setDetailedMapper(new GhostRowMapper(this));
        this.getFields().putAll(this.initFields());
    }

    @Override
    protected Object getValueForInsert(String field, IGhost essence) {
        switch (field){
            case IGhost.BEAN_NAME: return essence.getBeanName();
            case IGhost.FIELD_NAME: return essence.getFieldName();
            case IGhost.FIELD_TYPE: return essence.getFieldType() == null ? null: essence.getFieldType().toString();
            case IGhost.SEARCH_VALUE: return essence.getSearchValue();
            case IGhost.DT_LAST_SEND: return essence.getLastSend();
            case IGhost.RESPONSE_STATUS: return essence.getResponseStatus() == null ? null: essence.getResponseStatus().toString();
            case IGhost.RESPONSE_UUID: return essence.getResponseUuid();
            case IGhost.ESSENCE_KEY: return essence.getUuid();
            case IGhost.ESSENCE_DT_CREATE: return essence.getDtCreate();
            case IGhost.ESSENCE_DT_UPDATE: return essence.getDtUpdate();
            case IGhost.ESSENCE_SUMMARY: return essence.getSummary();
            default: return null;
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public Class<? super IGhost> getDtoClass() {
        return IGhost.class;
    }

    @Override
    public boolean isDtoSupported(Class<?> clazz) {
        return IGhost.class.isAssignableFrom(clazz);
    }


    @Transactional
    @Override
    public List<IGhostRequest> getGhostEssence(long ttl, int batchSize) {
        Date date = new Date();

        String SQL = String.format(SET_DT_LAST_TRY_INV_SQL_PATTERN,
                getSchemaName(), getTableName(),
                getSchemaName(), getTableName(),
                getTableName(),
                SIMPLE_DATE_FORMAT.format(date), ttl, batchSize,
                IEssence.ESSENCE_KEY,IGhost.BEAN_NAME,IGhost.FIELD_NAME,IGhost.FIELD_TYPE, IGhost.SEARCH_VALUE);

        List<IGhostRequest> requests = jdbcTemplate.query(SQL, new GhostRequestRowMapper(), date);

        return requests.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UUID create(IGhost ghost) {
        Date date = new Date(System.currentTimeMillis());
        ghost.setUuid(UUID.randomUUID());
        ghost.setDtCreate(date);
        ghost.setDtUpdate(date);
        ghost.setSummary(this.buildSummary(ghost));
        return super.create(ghost);
    }

    @Transactional
    @Override
    public void update(UUID id, Date lastDate, IGhost updatedInstance) {
        IGhost lastSaved = this.read(id);
        updatedInstance.setUuid(lastSaved.getUuid());
        updatedInstance.setDtCreate(lastSaved.getDtCreate());
        updatedInstance.setDtUpdate(new Date(System.currentTimeMillis()));
        updatedInstance.setSummary(this.buildSummary(updatedInstance));
        super.update(id, lastDate, updatedInstance);
    }


    @Override
    protected String buildSummary(IGhost iGhost) {
        return String.format("ghost: lst update - %s, created - %s", iGhost.getDtUpdate(),iGhost.getDtCreate());
    }

    private Map<String, IColumn> initFields(){
        Map<String, IColumn> columns = new HashMap<>();

        //ESSENCE

        //uuid
        IColumn uuidColumn = new Column();
        uuidColumn.setName(IEssence.ESSENCE_KEY);
        uuidColumn.setViewOnBrief(true);
        uuidColumn.setType(PGColumnType.IDENTIFICATION);
        columns.put(uuidColumn.getName(),uuidColumn);

        //dt_create
        IColumn createColumn = new Column();
        createColumn.setName(IEssence.ESSENCE_DT_CREATE);
        createColumn.setType(PGColumnType.DATETIME);
        createColumn.setViewOnBrief(true);
        columns.put(createColumn.getName(),createColumn);

        //dt_update
        IColumn updColumn = new Column();
        updColumn.setName(IEssence.ESSENCE_DT_UPDATE);
        updColumn.setType(PGColumnType.DATETIME);
        updColumn.setViewOnBrief(true);
        columns.put(updColumn.getName(),updColumn);

        //summary
        IColumn summaryColumn = new Column();
        summaryColumn.setName(IEssence.ESSENCE_SUMMARY);
        summaryColumn.setType(PGColumnType.STRING);
        summaryColumn.setViewOnBrief(true);
        columns.put(summaryColumn.getName(),summaryColumn);


        IColumn beanName = new Column();
        beanName.setName(IGhost.BEAN_NAME);
        beanName.setType(PGColumnType.STRING);
        beanName.setViewOnBrief(true);
        columns.put(beanName.getName(),beanName);

        IColumn columnName = new Column();
        columnName.setName(IGhost.FIELD_NAME);
        columnName.setType(PGColumnType.STRING);
        columnName.setViewOnBrief(true);
        columns.put(columnName.getName(),columnName);

        IColumn lastSend = new Column();
        lastSend.setName(IGhost.DT_LAST_SEND);
        lastSend.setType(PGColumnType.DATETIME);
        lastSend.setViewOnBrief(true);
        columns.put(lastSend.getName(),lastSend);

        IColumn fieldType = new Column();
        fieldType.setName(IGhost.FIELD_TYPE);
        fieldType.setType(PGColumnType.ENUM);
        fieldType.setClazz(FieldType.class);
        fieldType.setViewOnBrief(true);
        columns.put(fieldType.getName(),fieldType);

        IColumn value = new Column();
        value.setName(IGhost.SEARCH_VALUE);
        value.setType(PGColumnType.STRING);
        value.setViewOnBrief(true);
        columns.put(value.getName(), value);


        IColumn respUuid = new Column();
        respUuid.setName(IGhost.RESPONSE_UUID);
        respUuid.setType(PGColumnType.IDENTIFICATION);
        respUuid.setViewOnBrief(true);
        columns.put(respUuid.getName(),respUuid);

        IColumn respStatus = new Column();
        respStatus.setName(IGhost.RESPONSE_STATUS);
        respStatus.setType(PGColumnType.ENUM);
        respStatus.setClazz(GhostStatus.class);
        respStatus.setViewOnBrief(true);
        columns.put(respStatus.getName(),respStatus);


        return columns;

    };
}
