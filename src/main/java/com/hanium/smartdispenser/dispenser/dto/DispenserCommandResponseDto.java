package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.history.domain.HistoryStatus;

import java.time.LocalDateTime;

public record DispenserCommandResponseDto (
        String commandId,
        Long dispenserId,
        Long userId,
        Long recipeId,
        Long historyId,
        HistoryStatus status,
        LocalDateTime requestedAt,
        LocalDateTime completedAt
) {
}
