package com.hanium.smartdispenser.auth.dto;

import lombok.Getter;

@Getter
public class GuestLoginRequestDto {

    private final String uuid;

    public GuestLoginRequestDto(String uuid) {
        this.uuid = uuid;
    }
}
