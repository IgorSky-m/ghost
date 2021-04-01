package com.skachko.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("ghost.sender")
public class GhostProperties {
    private boolean enabled;
    private int ttl;
    private int rate;
    private int batch;
    private Map<String, String> receivers;
    private Database database;



    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, String> getReceivers() {
        return receivers;
    }

    public void setReceivers(Map<String, String> receivers) {
        this.receivers = receivers;
    }

    public Database getDatabase(){
        return this.database;
    }

    public void setDatabase(Database database){
        this.database = database;
    }

    public static class Database {
        private String schema;
        private String table;

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }
    }
}
