package com.hanium.smartdispenser.auth.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class InvalidLoginException extends BusinessException {

    public static final String DEFAULT_MESSAGE = "아이디 또는 패스워드가 올바르지 않습니다.";
    public static final String ERROR_CODE = "INVALID_LOGIN";

    public InvalidLoginException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public InvalidLoginException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause, ERROR_CODE);
    }
}
