package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.history.domain.HistoryStatus;

public record DispenserCommandSimpleResponseDto (
        String commandId,
        Long dispenserId,
        Long historyId,
        HistoryStatus status
) {
}
