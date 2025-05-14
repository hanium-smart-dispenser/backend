package com.hanium.smartdispenser.auth;

import com.hanium.smartdispenser.auth.dto.LoginRequestDto;
import com.hanium.smartdispenser.auth.dto.LoginResponseDto;
import com.hanium.smartdispenser.auth.dto.SignUpRequestDto;
import com.hanium.smartdispenser.auth.dto.SignUpResponseDto;
import com.hanium.smartdispenser.auth.exception.PasswordMismatchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> singUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getPasswordConfirm())) {
            throw new PasswordMismatchException();
        }
        return ResponseEntity
                .status(201).
                body(new SignUpResponseDto(loginService.singUp(signUpRequestDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(loginService.login(loginRequestDto));
    }

    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.ok().build();
    }

}
