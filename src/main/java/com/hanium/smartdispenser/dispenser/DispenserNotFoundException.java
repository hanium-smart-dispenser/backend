package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class DispenserNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "디스펜서를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "DISPENSER_NOT_FOUND";
    public DispenserNotFoundException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public DispenserNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause, ERROR_CODE);
    }
}
