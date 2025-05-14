package com.hanium.smartdispenser.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

    @JsonCreator
    public LoginRequestDto(@JsonProperty("email") String email,
                           @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
