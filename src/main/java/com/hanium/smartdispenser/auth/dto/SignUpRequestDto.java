package com.hanium.smartdispenser.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.user.dto.UserCreateDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private final String password;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private final String passwordConfirm;

    @JsonCreator
    public SignUpRequestDto(@JsonProperty("password") String password,
                            @JsonProperty("email") String email,
                            @JsonProperty("passwordConfirm") String passwordConfirm) {
        this.password = password;
        this.email = email;
        this.passwordConfirm = passwordConfirm;
    }

    public UserCreateDto toUserCreateDto() {
        return new UserCreateDto(getPassword(), getEmail());
    }
}
