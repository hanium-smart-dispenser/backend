package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class UnauthorizedDispenserAccessException extends BusinessException {

    public static final String DEFAULT_MESSAGE = "이 디스펜서에 접근 권한이 없습니다. (USER_ID:%d, DISPENSER_ID:%d)";
    public static final String ERROR_CODE = "DISPENSER_UNAUTHORIZED";
    public UnauthorizedDispenserAccessException(Long userId, Long dispenserId) {
        super(String.format(DEFAULT_MESSAGE,userId,dispenserId), ERROR_CODE);
    }

    public UnauthorizedDispenserAccessException(Throwable cause, Long userId, Long dispenserId) {
        super(String.format(DEFAULT_MESSAGE,userId,dispenserId), cause, ERROR_CODE);
    }
}
