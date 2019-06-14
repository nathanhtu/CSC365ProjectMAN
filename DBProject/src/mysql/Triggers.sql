-- Update student's book count when checking out book.
    DELIMITER $$
        CREATE TRIGGER update_numBooks_trigger BEFORE INSERT
            ON Checkout
            FOR EACH ROW BEGIN
            UPDATE Students SET Students.numBooks = Students.numBooks + 1
                WHERE Students.studentID = NEW.studentID;
            END$$
    DELIMITER ;

-- Update student's book count when checking in book.
    DELIMITER $$
        CREATE TRIGGER update_numBooksReturn_trigger BEFORE UPDATE
            ON Checkout
            FOR EACH ROW BEGIN
            IF OLD.checkinDate IS NULL AND NEW.checkinDate IS NOT NULL THEN
                UPDATE Students SET Students.numBooks = Students.numBooks - 1
                    WHERE Students.studentID = new.StudentID;
            END IF;
        END$$
    DELIMITER ;

-- Decreasing book stock once a book is checked out.
    DELIMITER $$
        CREATE TRIGGER update_bookStock_trigger BEFORE INSERT
            ON Checkout
            FOR EACH ROW BEGIN
            UPDATE Books SET Books.stock = Books.stock - 1
                WHERE Books.serial = NEW.serial;
        END$$
    DELIMITER ;

-- Increasing book stock once book is returned.
    DELIMITER $$
        CREATE TRIGGER update_bookStockReturn_trigger BEFORE UPDATE
            ON Checkout
            FOR EACH ROW BEGIN
            IF OLD.extended = NEW.extended THEN
                UPDATE Books SET Books.stock = Books.stock + 1
                    WHERE Books.serial = NEW.serial;
            END IF;
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

-- Checking if a book was already reserved
    DELIMITER $$
        CREATE TRIGGER checkoff_reservation AFTER INSERT
            ON Checkout
            FOR EACH ROW BEGIN
            UPDATE Reservations SET Reservations.checkedOut = 1
                WHERE Reservations.studentID = NEW.studentID;
        END$$
    DELIMITER ;
