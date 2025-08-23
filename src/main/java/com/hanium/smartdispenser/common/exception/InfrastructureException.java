package com.hanium.smartdispenser.common.exception;

import lombok.Getter;

import java.util.HashMap;

@Getter
public abstract class InfrastructureException extends RuntimeException {
    public final String errorCode;
    public final HashMap<String, Object> context = new HashMap<>();

    public InfrastructureException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public InfrastructureException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    public InfrastructureException put(String key, Object value) {
        if (value != null) {
            context.put(key, value);
        }
        return this;
    }
}
