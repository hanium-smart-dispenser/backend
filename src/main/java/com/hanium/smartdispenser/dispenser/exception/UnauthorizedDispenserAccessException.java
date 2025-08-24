package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnauthorizedDispenserAccessException extends BusinessException {

    public static final String DEFAULT_MESSAGE = "이 디스펜서에 접근 권한이 없습니다.";
    public static final String ERROR_CODE = "DISPENSER_UNAUTHORIZED";
    public UnauthorizedDispenserAccessException(Long dispenserId, Long userId) {
        super(DEFAULT_MESSAGE,ERROR_CODE);
        put("dispenserId", dispenserId);
        put("userId", userId);
    }
}
