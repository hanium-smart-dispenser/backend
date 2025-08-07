package com.hanium.smartdispenser.dispenser.dto;


import lombok.Getter;

@Getter
public class DispenserRegisterRequestDto {

    private final String uuid;

    public DispenserRegisterRequestDto(String uuid) {
        this.uuid = uuid;
    }
}
