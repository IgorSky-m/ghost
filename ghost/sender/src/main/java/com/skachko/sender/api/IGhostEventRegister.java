package com.skachko.sender.api;

public interface IGhostEventRegister {
    void register(Object value, String fieldName, String dtoBeanName);
}
