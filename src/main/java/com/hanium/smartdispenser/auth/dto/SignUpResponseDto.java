package com.hanium.smartdispenser.auth.dto;

import com.hanium.smartdispenser.user.domain.User;
import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private final Long id;
    private final String email;
    private final String name;
    private final String createdAt;

    public SignUpResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.createdAt = String.valueOf(user.getCreatedAt());
    }
}
