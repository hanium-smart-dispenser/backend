package com.hanium.smartdispenser.user.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class DuplicateEmailException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "이미 사용 중인 이메일(%s)입니다.";
    public static final String ERROR_CODE = "USER_DUPLICATE_EMAIL";

    public DuplicateEmailException(String email) {
        super(String.format(DEFAULT_MESSAGE, email), ERROR_CODE);
    }

    public DuplicateEmailException(Throwable cause, String email) {
        super(String.format(DEFAULT_MESSAGE, email), cause, ERROR_CODE);
    }
}
