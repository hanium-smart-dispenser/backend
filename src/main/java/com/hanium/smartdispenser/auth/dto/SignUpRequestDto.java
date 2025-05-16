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
    private final String name;

    @NotBlank
    private final String password;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String passwordConfirm;

    @JsonCreator
    public SignUpRequestDto(@JsonProperty("name") String name,
                            @JsonProperty("password") String password,
                            @JsonProperty("email") String email,
                            @JsonProperty("passwordConfirm") String passwordConfirm) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.passwordConfirm = passwordConfirm;
    }

    public UserCreateDto toUserCreateDto() {
        return new UserCreateDto(getName(), getPassword(), getEmail());
    }
}
