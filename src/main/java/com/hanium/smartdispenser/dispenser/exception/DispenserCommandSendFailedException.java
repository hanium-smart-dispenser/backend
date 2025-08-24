package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.InfrastructureException;


public class DispenserCommandSendFailedException extends InfrastructureException {

    public static final String DEFAULT_MESSAGE = "디스펜서에 명령 전송에 실패했습니다.";
    public static final String ERROR_CODE = "DISPENSER_COMMAND_SEND_FAILED";

    public DispenserCommandSendFailedException(Long dispenserId, String payLoad) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("dispenserId", dispenserId);
        put("payload", payLoad);
    }
}
