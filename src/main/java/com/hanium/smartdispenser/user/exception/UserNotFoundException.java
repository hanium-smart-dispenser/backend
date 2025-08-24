package com.hanium.smartdispenser.user.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "사용자를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "USER_NOT_FOUND";

    public UserNotFoundException(String email) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("email", email);
    }
    public UserNotFoundException(Long id) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("id", id);
    }
}
