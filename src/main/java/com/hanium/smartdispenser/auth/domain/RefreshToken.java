package com.hanium.smartdispenser.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String token;
    private LocalDateTime expiredAt;


    public static RefreshToken of(Long userId, String token, LocalDateTime expiredAt) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.userId = userId;
        refreshToken.token = token;
        refreshToken.expiredAt = expiredAt;
        return refreshToken;
    }
}
