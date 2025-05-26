package com.hanium.smartdispenser.auth.dto;

import lombok.Getter;

@Getter
public class AccessTokenResponseDto {

    private final String accessToken;

    public AccessTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
