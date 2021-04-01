package com.skachko.sender.enums;

public enum MessageType {
    REQUEST("request"),
    RESPONSE("response");

    public final String suffix;

    MessageType(String suffix) {
        this.suffix = suffix;
    }
}
