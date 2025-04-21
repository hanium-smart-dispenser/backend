package com.hanium.smartdispenser.common.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    public final String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
