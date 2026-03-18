package com.example.library.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @NotBlank(message = "手機號碼不可空白")
        @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式錯誤")
        String phoneNumber,

        @NotBlank(message = "密碼不可空白")
        String password
) {}