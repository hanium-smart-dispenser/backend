package com.hanium.smartdispenser.common.exception;

import lombok.Getter;

@Getter
public abstract class InfrastructureException extends RuntimeException {
    public final String errorCode;

    public InfrastructureException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public InfrastructureException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
