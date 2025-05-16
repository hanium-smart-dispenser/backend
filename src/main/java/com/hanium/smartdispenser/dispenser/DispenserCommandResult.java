package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.history.domain.HistoryStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DispenserCommandResult {

    private final String commandId;
    private final HistoryStatus status;
    private final LocalDateTime requestedAt;
    private final LocalDateTime completedAt;

    public DispenserCommandResult(String commandId, HistoryStatus status, LocalDateTime requestedAt, LocalDateTime completedAt) {
        this.commandId = commandId;
        this.status = status;
        this.requestedAt = requestedAt;
        this.completedAt =completedAt;
    }
}
