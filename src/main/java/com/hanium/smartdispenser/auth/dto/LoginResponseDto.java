package com.hanium.smartdispenser.auth.dto;

import com.hanium.smartdispenser.user.domain.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String accessToken;
    private final long id;
    private final String name;
    private final String email;
    private final String role;

    public LoginResponseDto(String accessToken, User user) {
        this.accessToken = accessToken;
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = String.valueOf(user.getRole());
    }
}
