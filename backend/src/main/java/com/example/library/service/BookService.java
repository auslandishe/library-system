package com.example.library.service;

import com.example.library.common.dto.response.BookItemResponse;
import com.example.library.common.dto.response.BorrowRecordResponse;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookItemResponse> findAllBooks() {
        return bookRepository.findAllInventoryBooks();
    }

    public List<BorrowRecordResponse> findMyBorrowedBooks(Long userId) {
        return bookRepository.findMyBorrowedBooks(userId);
    }
}