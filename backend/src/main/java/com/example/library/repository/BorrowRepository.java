package com.example.library.repository;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;

@Repository
public class BorrowRepository {

    private final JdbcTemplate jdbcTemplate;

    public BorrowRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void borrowBook(Long userId, Long inventoryId) {
        jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
            try (CallableStatement cs = connection.prepareCall("{call sp_borrow_book(?, ?)}")) {
                cs.setLong(1, userId);
                cs.setLong(2, inventoryId);
                cs.execute();
            }
            return null;
        });
    }

    public void returnBook(Long userId, Long inventoryId) {
        jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
            try (CallableStatement cs = connection.prepareCall("{call sp_return_book(?, ?)}")) {
                cs.setLong(1, userId);
                cs.setLong(2, inventoryId);
                cs.execute();
            }
            return null;
        });
    }
}