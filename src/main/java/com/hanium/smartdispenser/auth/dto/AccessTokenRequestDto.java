package com.hanium.smartdispenser.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.user.domain.UserRole;
import lombok.Getter;

@Getter
public class AccessTokenRequestDto {

    private final String refreshToken;
    private final UserRole userRole;

    @JsonCreator
    public AccessTokenRequestDto(@JsonProperty("refreshToken") String refreshToken, UserRole userRole) {
        this.refreshToken = refreshToken;
        this.userRole = userRole;
    }
}
