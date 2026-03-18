CREATE DATABASE IF NOT EXISTS library_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE library_system;

DROP TABLE IF EXISTS borrowing_records;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       phone_number VARCHAR(20) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       user_name VARCHAR(100) NOT NULL,
                       registration_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       last_login_time DATETIME NULL,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE books (
                       isbn VARCHAR(20) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       introduction TEXT,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE inventory (
                           inventory_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           isbn VARCHAR(20) NOT NULL,
                           store_time DATETIME NOT NULL,
                           status VARCHAR(20) NOT NULL,
                           location VARCHAR(100),
                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           CONSTRAINT fk_inventory_book FOREIGN KEY (isbn) REFERENCES books(isbn)
);

CREATE TABLE borrowing_records (
                                   record_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   user_id BIGINT NOT NULL,
                                   inventory_id BIGINT NOT NULL,
                                   borrowing_time DATETIME NOT NULL,
                                   return_time DATETIME NULL,
                                   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   CONSTRAINT fk_record_user FOREIGN KEY (user_id) REFERENCES users(user_id),
                                   CONSTRAINT fk_record_inventory FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id),
                                   INDEX idx_record_user (user_id),
                                   INDEX idx_record_inventory (inventory_id)
);