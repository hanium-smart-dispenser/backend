package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class DispenserNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "디스펜서(%d)를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "DISPENSER_NOT_FOUND";
    public DispenserNotFoundException(Long dispenserId) {
        super(String.format(DEFAULT_MESSAGE, dispenserId), ERROR_CODE);
    }

    public DispenserNotFoundException(Throwable cause, Long dispenserId) {
        super(String.format(DEFAULT_MESSAGE, dispenserId), cause, ERROR_CODE);
    }
}
