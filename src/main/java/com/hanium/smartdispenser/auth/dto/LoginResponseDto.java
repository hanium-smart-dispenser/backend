package com.hanium.smartdispenser.auth.dto;

import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.domain.UserRole;

public record LoginResponseDto (
        String accessToken,
        String refreshToken,
        Long id,
        String email,
        UserRole role
) {
    public LoginResponseDto(String accessToken, String refreshToken, User user) {
        this(accessToken, refreshToken, user.getId(), user.getEmail(), user.getUserRole());
    }
}
