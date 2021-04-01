package com.skachko.receiver.exceptions;

public class ReadRequestException extends RuntimeException{
    public ReadRequestException() {
        super();
    }

    public ReadRequestException(String message) {
        super(message);
    }

    public ReadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadRequestException(Throwable cause) {
        super(cause);
    }

    protected ReadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
