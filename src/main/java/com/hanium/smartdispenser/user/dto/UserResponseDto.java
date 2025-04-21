package com.hanium.smartdispenser.user.dto;


import com.hanium.smartdispenser.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {

    private final String name;
    private final String email;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getName(), user.getEmail());
    }

}
