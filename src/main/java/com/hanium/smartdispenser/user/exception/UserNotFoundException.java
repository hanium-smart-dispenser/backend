package com.hanium.smartdispenser.user.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "사용자(%s)를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "USER_NOT_FOUND";

    public UserNotFoundException(String email) {
        super(String.format(DEFAULT_MESSAGE, email), ERROR_CODE);
    }
    public UserNotFoundException(Throwable cause, String email) {
        super(String.format(DEFAULT_MESSAGE, email), cause, ERROR_CODE);
    }

    public UserNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id), ERROR_CODE);
    }

    public UserNotFoundException(Throwable cause, Long id) {
        super(String.format(DEFAULT_MESSAGE, id), cause, ERROR_CODE);
    }
}
