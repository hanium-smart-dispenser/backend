package com.hanium.smartdispenser.history;

import com.hanium.smartdispenser.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HistoryNotFound extends BusinessException {

    public static final String DEFAULT_MESSAGE = "히스토리를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "HISTORY_NOT_FOUND";
    public HistoryNotFound(Long historyId) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("historyId", historyId);
    }
}
