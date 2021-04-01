DROP SCHEMA IF EXISTS ghost CASCADE;

CREATE SCHEMA ghost;

CREATE TABLE ghost.ghost (
    uuid UUID PRIMARY KEY NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone,
    summary varchar(255),
    field_name varchar(255),
    field_type varchar(50),
    bean_name varchar(255),
    search_value varchar(255),
    dt_last_send timestamp without time zone,
    response_status varchar(50),
    response_uuid uuid
);

