package com.example.library.controller;

import com.example.library.common.dto.response.ApiResponse;
import com.example.library.common.dto.request.LoginRequest;
import com.example.library.common.dto.request.RegisterRequest;
import com.example.library.common.dto.response.LoginResponse;
import com.example.library.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ApiResponse.success("註冊成功", null);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success("登入成功", authService.login(request));
    }
}