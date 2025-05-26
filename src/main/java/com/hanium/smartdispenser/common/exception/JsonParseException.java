package com.hanium.smartdispenser.common.exception;

public class JsonParseException extends InfrastructureException {

    public static final String DEFAULT_MESSAGE = "JSON 직렬화에 실패했습니다.";
    public static final String ERROR_CODE = "JSON_PARSE_ERROR";

    public JsonParseException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public JsonParseException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause, ERROR_CODE);
    }
}
