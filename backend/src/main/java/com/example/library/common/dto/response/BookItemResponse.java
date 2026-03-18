package com.example.library.common.dto.response;

public record BookItemResponse(
        Long inventoryId,
        String isbn,
        String bookName,
        String author,
        String introduction,
        String status,
        String location
) {
}
