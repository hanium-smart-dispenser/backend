package com.hanium.smartdispenser.dispenser.dto;


import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DispenserDto {

    private final Long dispenserId;
    private final DispenserStatus status;


    public static DispenserDto of(Dispenser dispenser) {
        return new DispenserDto(dispenser.getId(), dispenser.getStatus());
    }
}
