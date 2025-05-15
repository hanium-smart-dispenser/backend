package com.hanium.smartdispenser.dispenser;


import lombok.Getter;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@Getter
public class DispenserCommandRequestDto {
    private final String commandId;
    private final Long userId;
    private final LocalDateTime requestedAt;

    public DispenserCommandRequestDto(String commandId, Long userId) {
        this.commandId = commandId;
        this.userId = userId;
        this.requestedAt = now();
    }
}
