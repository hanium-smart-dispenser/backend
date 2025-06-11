package com.hanium.smartdispenser.auth;

import com.hanium.smartdispenser.auth.dto.LoginRequestDto;
import com.hanium.smartdispenser.auth.dto.LoginResponseDto;
import com.hanium.smartdispenser.auth.dto.SignUpRequestDto;
import com.hanium.smartdispenser.auth.exception.InvalidLoginException;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.exception.UserNotFoundException;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            User user = userService.findByEmail(loginRequestDto.getEmail());

            if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                throw new InvalidLoginException();
            }

            String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getUserRole());
            String refreshToken = UUID.randomUUID().toString();
            refreshTokenService.save(refreshToken, user.getId());

            return new LoginResponseDto(accessToken, refreshToken, user);
        } catch (UserNotFoundException e) {
            throw new InvalidLoginException(e);
        }
    }

    public User singUp(SignUpRequestDto signUpRequestDto) {
        return userService.createUser(signUpRequestDto.toUserCreateDto());
    }
}
