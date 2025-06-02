package com.hanium.smartdispenser.user.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserCreateDto {
    private final String email;
    private final String password;

    @JsonCreator
    public UserCreateDto(@JsonProperty("password") String password,
                         @JsonProperty("email") String email) {

        this.password = password;
        this.email = email;
    }


}
