package com.hanium.smartdispenser.user.controller;

import com.hanium.smartdispenser.user.dto.UserCreateDto;
import com.hanium.smartdispenser.user.dto.UserResponseDto;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

}