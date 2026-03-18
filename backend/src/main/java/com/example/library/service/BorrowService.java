package com.example.library.service;

import com.example.library.repository.BorrowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Transactional
    public void borrow(Long userId, Long inventoryId) {
        borrowRepository.borrowBook(userId, inventoryId);
    }

    @Transactional
    public void returnBook(Long userId, Long inventoryId) {
        borrowRepository.returnBook(userId, inventoryId);
    }
}