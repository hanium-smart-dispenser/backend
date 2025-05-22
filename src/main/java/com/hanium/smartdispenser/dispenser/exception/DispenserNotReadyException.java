package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;

public class DispenserNotReadyException extends BusinessException {

    public static final String DEFAULT_MESSAGE = "디스펜서가 준비되지 않았습니다. 현재 상태 = [%s]";
    public static final String ERROR_CODE = "DISPENSER_NOT_READY";
    public DispenserNotReadyException(DispenserStatus status) {
        super(String.format(DEFAULT_MESSAGE, status), ERROR_CODE);
    }

    public DispenserNotReadyException(Throwable cause, DispenserStatus status) {
        super(String.format(DEFAULT_MESSAGE, status), cause, ERROR_CODE);
    }
}
