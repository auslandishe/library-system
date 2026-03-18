USE library_system;

DROP PROCEDURE IF EXISTS sp_borrow_book;
DROP PROCEDURE IF EXISTS sp_return_book;

DELIMITER $$

CREATE PROCEDURE sp_borrow_book(
    IN p_user_id BIGINT,
    IN p_inventory_id BIGINT
)
BEGIN
    DECLARE v_status VARCHAR(20);
    DECLARE v_open_count INT DEFAULT 0;

    START TRANSACTION;

    SELECT status
    INTO v_status
    FROM inventory
    WHERE inventory_id = p_inventory_id
        FOR UPDATE;

    IF v_status IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '找不到該書籍庫存';
    END IF;

    IF v_status <> 'AVAILABLE' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '此書目前不可借閱';
    END IF;

    SELECT COUNT(*)
    INTO v_open_count
    FROM borrowing_records
    WHERE inventory_id = p_inventory_id
      AND return_time IS NULL;

    IF v_open_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '此書已被借出';
    END IF;

    UPDATE inventory
    SET status = 'BORROWED'
    WHERE inventory_id = p_inventory_id;

    INSERT INTO borrowing_records (
        user_id,
        inventory_id,
        borrowing_time
    ) VALUES (
                 p_user_id,
                 p_inventory_id,
                 NOW()
             );

    COMMIT;
END$$

DELIMITER $$

CREATE PROCEDURE sp_return_book(
    IN p_user_id BIGINT,
    IN p_inventory_id BIGINT
)
BEGIN
    DECLARE v_record_id BIGINT;

    START TRANSACTION;

    SELECT record_id
    INTO v_record_id
    FROM borrowing_records
    WHERE user_id = p_user_id
      AND inventory_id = p_inventory_id
      AND return_time IS NULL
    ORDER BY borrowing_time DESC
    LIMIT 1
    FOR UPDATE;

    IF v_record_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '查無未歸還借閱紀錄';
    END IF;

    UPDATE borrowing_records
    SET return_time = NOW()
    WHERE record_id = v_record_id;

    UPDATE inventory
    SET status = 'AVAILABLE'
    WHERE inventory_id = p_inventory_id;

    COMMIT;
END$$

DELIMITER ;
DELIMITER ;