package com.hanium.smartdispenser.user.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class DuplicateEmailException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "이미 사용 중인 이메일입니다.";
    private static final String ERROR_CODE = "USER_DUPLICATE_EMAIL";

    public DuplicateEmailException() {
        super(DEFAULT_MESSAGE,ERROR_CODE);
    }

    public DuplicateEmailException(String message, String errCode) {
        super(message, errCode);
    }

    public DuplicateEmailException(Throwable cause, String errCode) {
        super(cause, errCode);
    }

    public DuplicateEmailException(String message, Throwable cause, String errCode) {
        super(message, cause, errCode);
    }
}
