package com.hanium.smartdispenser.auth;

import com.hanium.smartdispenser.auth.domain.RefreshToken;
import com.hanium.smartdispenser.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    // JwtConfig 에서 secretKey 주입받음
    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.token.expiration.access}")
    private Long accessTokenExpiration;

    @Value("${jwt.token.expiration.refresh}")
    private Long refreshTokenExpiration;

    /**
     *  Token 안에는 sub, userRole, iat, exp 있음.
     */
    public String createAccessToken(Long userId, UserRole userRole) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("userRole", userRole)
                .issuedAt(now)
                .expiration(expire)
                .signWith(secretKey)
                .compact();
    }

    @Transactional
    public String createRefreshToken(Long userId) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + refreshTokenExpiration);

        String token = Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expire)
                .signWith(secretKey)
                .compact();
        RefreshToken refreshToken = RefreshToken.of(userId, token, expire.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        String userId = claims.getSubject();
        String role = claims.get("role", String.class);
        UserPrincipal user = new UserPrincipal(Long.valueOf(userId), role);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return Long.valueOf(claims.getSubject());
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }

}