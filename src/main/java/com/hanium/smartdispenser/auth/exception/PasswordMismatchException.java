package com.hanium.smartdispenser.auth.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class PasswordMismatchException extends BusinessException {

    public static final String DEFAULT_MESSAGE = "비밀번호가 일치하지 않습니다.";
    public static final String ERROR_CODE = "PASSWORD_MISMATCH";

    public PasswordMismatchException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public PasswordMismatchException(Throwable cause){
        super(DEFAULT_MESSAGE, cause, ERROR_CODE);
    }
}
