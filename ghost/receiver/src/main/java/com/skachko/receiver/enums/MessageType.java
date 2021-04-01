package com.skachko.receiver.enums;

public enum MessageType {

    REQUEST("request"),
    RESPONSE("response");

    public final String suffix;

    MessageType(String suffix) {
        this.suffix = suffix;
    }
}
