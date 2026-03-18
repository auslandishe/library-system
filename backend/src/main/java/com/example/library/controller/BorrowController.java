package com.example.library.controller;

import com.example.library.common.dto.response.ApiResponse;
import com.example.library.common.dto.request.BorrowRequest;
import com.example.library.common.dto.request.ReturnRequest;
import com.example.library.service.BorrowService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    public ApiResponse<Void> borrow(HttpServletRequest request,
                                    @Valid @RequestBody BorrowRequest borrowRequest) {
        Long userId = (Long) request.getAttribute("userId");
        borrowService.borrow(userId, borrowRequest.inventoryId());
        return ApiResponse.success("借書成功", null);
    }

    @PostMapping("/return")
    public ApiResponse<Void> returnBook(HttpServletRequest request,
                                        @Valid @RequestBody ReturnRequest returnRequest) {
        Long userId = (Long) request.getAttribute("userId");
        borrowService.returnBook(userId, returnRequest.inventoryId());
        return ApiResponse.success("還書成功", null);
    }
}