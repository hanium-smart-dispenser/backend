package com.hanium.smartdispenser.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.user.dto.UserCreateDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

    @NotBlank
    private final String name;

    @NotBlank
    private final String passwordConfirm;

    @JsonCreator
    public SignUpRequestDto(@JsonProperty("email") String email,
                            @JsonProperty("password") String password,
                            @JsonProperty("name") String name,
                            @JsonProperty("passwordConfirm") String passwordConfirm) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.passwordConfirm = passwordConfirm;
    }

    public UserCreateDto toUserCreateDto() {
        return new UserCreateDto(getName(), getEmail(), getPassword());
    }

}
