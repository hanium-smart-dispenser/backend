package com.hanium.smartdispenser.auth.dto;

import com.hanium.smartdispenser.user.domain.User;

import java.time.LocalDateTime;

public record SignUpResponseDto (
        Long userId,
        String email,
        LocalDateTime createdAt
) {
    public SignUpResponseDto(User user) {
        this(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}
