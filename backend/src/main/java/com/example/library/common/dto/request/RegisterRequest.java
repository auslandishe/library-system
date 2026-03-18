package com.example.library.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "手機號碼不可空白")
        @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式錯誤")
        String phoneNumber,

        @NotBlank(message = "密碼不可空白")
        @Size(min = 8, max = 20, message = "密碼長度需為 8~20")
        String password,

        @NotBlank(message = "使用者名稱不可空白")
        @Size(max = 100, message = "使用者名稱不可超過 100 字")
        String userName
) {}