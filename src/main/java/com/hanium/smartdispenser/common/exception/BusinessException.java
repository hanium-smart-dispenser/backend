package com.hanium.smartdispenser.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String errCode;

    public BusinessException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public BusinessException(Throwable cause, String errCode) {
        super(cause);
        this.errCode = errCode;
    }

    public BusinessException(String message, Throwable cause, String errCode) {
        super(message, cause);
        this.errCode = errCode;
    }


}
