package com.example.library.controller;

import com.example.library.common.dto.response.ApiResponse;
import com.example.library.common.dto.response.BookItemResponse;
import com.example.library.common.dto.response.BorrowRecordResponse;
import com.example.library.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ApiResponse<List<BookItemResponse>> findAllBooks() {
        return ApiResponse.success("查詢成功", bookService.findAllBooks());
    }

    @GetMapping("/my-borrows")
    public ApiResponse<List<BorrowRecordResponse>> findMyBorrowedBooks(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ApiResponse.success("查詢成功", bookService.findMyBorrowedBooks(userId));
    }
}