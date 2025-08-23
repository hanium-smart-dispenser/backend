package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;

public class DispenserNotReadyException extends BusinessException {

    public static final String DEFAULT_MESSAGE = "디스펜서가 준비되지 않았습니다. 현재 상태 = [%s]";
    public static final String ERROR_CODE = "DISPENSER_NOT_READY";
    public DispenserNotReadyException(Long dispenserId, DispenserStatus status) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("dispenserId", dispenserId);
        put("status", status);
    }
}
