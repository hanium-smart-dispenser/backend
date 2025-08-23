package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispenserNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "디스펜서를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "DISPENSER_NOT_FOUND";

    public DispenserNotFoundException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public static DispenserNotFoundException byDispenserId(Long dispenserId) {
        DispenserNotFoundException ex = new DispenserNotFoundException();
        log.warn("디스펜서를 찾을 수 없습니다. dispenserId={}", dispenserId);
        return ex;
    }

    public static DispenserNotFoundException byUserId(Long userId) {
        DispenserNotFoundException ex = new DispenserNotFoundException();
        log.warn("디스펜서를 찾을 수 없습니다. userId={}", userId);
        return ex;
    }

    public static DispenserNotFoundException byUuid(String uuid) {
        DispenserNotFoundException ex = new DispenserNotFoundException();
        log.warn("디스펜서를 찾을 수 없습니다. uuid={}", uuid);
        return ex;
    }
}
