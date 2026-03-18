package com.example.library.common.dto.request;

import jakarta.validation.constraints.NotNull;

public record BorrowRequest(
        @NotNull(message = "inventoryId 不可空白")
        Long inventoryId
) {}