package com.hanium.smartdispenser.auth.dto;

import com.hanium.smartdispenser.user.domain.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String accessToken;
    private final String refreshToken;
    private final long id;
    private final String email;
    private final String role;

    public LoginResponseDto(String accessToken, String refreshToken, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = String.valueOf(user.getUserRole());
    }
}
