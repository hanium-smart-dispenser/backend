package com.hanium.smartdispenser.common.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final String errCode;

    public BusinessException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public BusinessException(String message, Throwable cause, String errCode) {
        super(message, cause);
        this.errCode = errCode;
    }
}
