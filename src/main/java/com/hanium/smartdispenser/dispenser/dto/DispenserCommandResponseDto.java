package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.history.domain.HistoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DispenserCommandResponseDto {

    private final String commandId;
    private final Long dispenserId;
    private final Long userId;
    private final Long recipeId;
    private final Long historyId;
    private final HistoryStatus status;
    private final LocalDateTime requestedAt;
    private final LocalDateTime completedAt;
}
