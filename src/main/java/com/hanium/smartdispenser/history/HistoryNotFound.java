package com.hanium.smartdispenser.history;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class HistoryNotFound extends BusinessException {

    public static final String DEFAULT_MESSAGE = "히스토리(%d)를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "HISTORY_NOT_FOUND";

    public HistoryNotFound(Long historyId) {
        super(String.format(DEFAULT_MESSAGE, historyId), ERROR_CODE);
    }

    public HistoryNotFound(Throwable cause, Long historyId) {
        super(String.format(DEFAULT_MESSAGE, historyId), cause, ERROR_CODE);
    }
}
