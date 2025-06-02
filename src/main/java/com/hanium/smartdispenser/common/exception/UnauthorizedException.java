package com.hanium.smartdispenser.common.exception;

public class UnauthorizedException extends InfrastructureException {

    public static final String DEFAULT_MESSAGE = "로그인이 필요합니다.";
    public static final String ERROR_CODE = "AUTH_401";

    public UnauthorizedException() {
        super(DEFAULT_MESSAGE,ERROR_CODE);
    }
    public UnauthorizedException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause, ERROR_CODE);
    }
}
