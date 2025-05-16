package com.hanium.smartdispenser.dispenser.exception;

import com.hanium.smartdispenser.common.exception.InfrastructureException;

public class DispenserCommandSendFailedException extends InfrastructureException {

    public static final String DEFAULT_MESSAGE = "디스펜서에 명령 전송에 실패했습니다.";
    public static final String ERROR_CODE = "DISPENSER_COMMAND_SEND_FAILED";

    public DispenserCommandSendFailedException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public DispenserCommandSendFailedException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause, ERROR_CODE);
    }
}
