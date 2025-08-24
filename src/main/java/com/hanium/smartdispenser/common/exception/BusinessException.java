package com.hanium.smartdispenser.common.exception;

import lombok.Getter;

import java.util.HashMap;

@Getter
public abstract class BusinessException extends RuntimeException {
    public final String errorCode;
    public final HashMap<String, Object> context = new HashMap<>();

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessException put(String key, Object value) {
        if (value != null) {
            context.put(key, value);
        }
        return this;
    }
}
