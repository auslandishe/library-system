package com.example.library.common.dto.response;

public record LoginResponse(
        String token,
        Long userId,
        String userName
) {}
