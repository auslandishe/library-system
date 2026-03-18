package com.example.library.repository;

import com.example.library.common.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM users WHERE phone_number = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, phoneNumber);
        return count != null && count > 0;
    }

    public int insert(String phoneNumber, String password, String userName) {
        String sql = """
                INSERT INTO users (phone_number, password, user_name, registration_time)
                VALUES (?, ?, ?, NOW())
                """;
        return jdbcTemplate.update(sql, phoneNumber, password, userName);
    }

    public User findByPhoneNumber(String phoneNumber) {
        String sql = """
                SELECT
                    user_id AS userId,
                    phone_number AS phoneNumber,
                    password AS password,
                    user_name AS userName,
                    registration_time AS registrationTime,
                    last_login_time AS lastLoginTime
                FROM users
                WHERE phone_number = ?
                """;
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), phoneNumber);
        return users.isEmpty() ? null : users.get(0);
    }

    public int updateLastLoginTime(Long userId) {
        String sql = "UPDATE users SET last_login_time = NOW() WHERE user_id = ?";
        return jdbcTemplate.update(sql, userId);
    }
}