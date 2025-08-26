package com.hanium.smartdispenser.auth.dto;

import com.hanium.smartdispenser.user.domain.UserRole;

public record AccessTokenRequestDto (
        String refreshToken,
        UserRole userRole
) {
}
