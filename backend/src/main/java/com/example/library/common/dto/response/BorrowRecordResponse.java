package com.example.library.common.dto.response;

import java.time.LocalDateTime;

public record BorrowRecordResponse(Long inventoryId,
                                   String isbn,
                                   String bookName,
                                   String author,
                                   LocalDateTime borrowingTime,
                                   String status,
                                   String location){

}
