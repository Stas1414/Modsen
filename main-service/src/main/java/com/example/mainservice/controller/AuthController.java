package com.example.mainservice.controller;

import com.example.mainservice.cfg.JwtProvider;
import com.example.mainservice.models.AuthRegRequest;
import com.example.mainservice.models.AuthResponse;
import com.example.mainservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация")
    @PostMapping("/sign-up")
    public AuthResponse reg(@RequestBody AuthRegRequest request) {
        return authService.signUp(request);
    }

    @Operation(summary = "Авторизация")
    @PostMapping("/sign-in")
    public AuthResponse login(@RequestBody AuthRegRequest request) {
        return authService.signIn(request);
    }

}
