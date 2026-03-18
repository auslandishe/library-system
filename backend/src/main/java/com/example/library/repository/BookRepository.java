package com.example.library.repository;

import com.example.library.common.dto.response.BookItemResponse;
import com.example.library.common.dto.response.BorrowRecordResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookItemResponse> findAllInventoryBooks() {
        String sql = """
                SELECT
                    i.inventory_id,
                    i.isbn,
                    b.name AS book_name,
                    b.author,
                    b.introduction,
                    i.status,
                    i.location
                FROM inventory i
                JOIN books b ON i.isbn = b.isbn
                ORDER BY i.inventory_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new BookItemResponse(
                rs.getLong("inventory_id"),
                rs.getString("isbn"),
                rs.getString("book_name"),
                rs.getString("author"),
                rs.getString("introduction"),
                rs.getString("status"),
                rs.getString("location")
        ));
    }

    public List<BorrowRecordResponse> findMyBorrowedBooks(Long userId) {
        String sql = """
                SELECT
                    br.inventory_id,
                    i.isbn,
                    b.name AS book_name,
                    b.author,
                    br.borrowing_time,
                    i.status,
                    i.location
                FROM borrowing_records br
                JOIN inventory i ON br.inventory_id = i.inventory_id
                JOIN books b ON i.isbn = b.isbn
                WHERE br.user_id = ?
                  AND br.return_time IS NULL
                ORDER BY br.borrowing_time DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new BorrowRecordResponse(
                rs.getLong("inventory_id"),
                rs.getString("isbn"),
                rs.getString("book_name"),
                rs.getString("author"),
                rs.getTimestamp("borrowing_time").toLocalDateTime(),
                rs.getString("status"),
                rs.getString("location")
        ), userId);
    }
}