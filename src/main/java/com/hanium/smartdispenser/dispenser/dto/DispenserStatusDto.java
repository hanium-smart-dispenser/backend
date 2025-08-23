package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;

import java.util.List;

public record DispenserStatusDto(
        String uuid,
        List<SauceDto> sauces
) {
    public DispenserStatusDto(Dispenser dispenser) {
        this(dispenser.getUuid(), dispenser.getDispenserSauces().stream().map(SauceDto::new).toList());
    }
}
