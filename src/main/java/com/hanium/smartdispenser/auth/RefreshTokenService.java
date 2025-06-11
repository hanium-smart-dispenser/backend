package com.hanium.smartdispenser.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, Long> redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    @Value("${jwt.token.expiration.refresh}")
    private Long ttl;

    public void save(String refreshToken, Long userId) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + refreshToken, userId, Duration.ofSeconds(ttl));
    }

    public Long get(String refreshToken) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + refreshToken);
    }

    public void delete(String refreshToken) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + refreshToken);
    }

    public boolean validate(String token, Long userId) {
        Long stored = get(token);
        return stored != null && stored.equals(userId);
    }


}
