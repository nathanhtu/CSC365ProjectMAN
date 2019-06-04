-- Update student's book count when checking out book.
    DELIMITER $$
        CREATE TRIGGER update_numBooks_trigger BEFORE INSERT
        ON Checkout
        FOR EACH ROW BEGIN
        UPDATE Students SET Students.numBooks = Students.numBooks + 1
            WHERE Students.studentID = Checkout.studentID;
        END$$
    DELIMITER ;

-- Decreasing book stock once a book is checked out.
    DELIMITER $$
        CREATE TRIGGER update_bookStock_trigger BEFORE INSERT
        ON Checkout
        FOR EACH ROW BEGIN
        UPDATE Books SET Books.stock = Books.stock - 1
            WHERE Books.serial = Checkout.serial;
        END$$
    DELIMITER ;

-- Checking if undergraduate students have reached max number of books
    DELIMITER $$
        CREATE TRIGGER check_UGnumBooks_trigger BEFORE UPDATE
        ON Students
        FOR EACH ROW BEGIN
        IF OLD.status = 'UG' AND NEW.numBooks > 3 THEN
            SIGNAL SQLSTATE '12345'
                SET MESSAGE_TEXT = 'ERROR: Reached maximum checkout for undergraduate students';
        END IF;
    END$$
    DELIMITER ;

-- Checking if graduate students have reached max number of books
    DELIMITER $$
        CREATE TRIGGER check_GRnumBooks_trigger BEFORE UPDATE
        ON Students
        FOR EACH ROW BEGIN
        IF OLD.status = 'GR' AND NEW.numBooks > 5 THEN
            SIGNAL SQLSTATE '12345'
                SET MESSAGE_TEXT = 'ERROR: Reached maximum checkout for graduate books students';
        END IF;
    END$$
    DELIMITER ;
