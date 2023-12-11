CREATE DATABASE LSD;

USE LSD;

/***************************************************************
 *                                                             *
 *                  EXTERNAL VALIDATION SERVICE                *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `evc` table stores external validation information    *
 *   for users. It is primarily used to validate user details  *
 *   from an external source.                                  *
 *                                                             *
 * Columns:                                                    *
 *   - `cnic`: CNIC (Computerized National Identity Card)      *
 *              number for user identification.                *
 *   - `name`: User's full name.                               *
 *   - `date_of_birth`: User's date of birth (YYYY-MM-DD).     *
 *   - `gender`: User's gender (Male/Female).                  *
 *                                                             *
 * Constraints:                                                *
 *   - `cnic` is the primary key.                              *
 *                                                             *
 ***************************************************************/
 
-- External Validation Service
CREATE TABLE `evc` (
    `cnic` VARCHAR(13),
    `name` VARCHAR(50) NOT NULL,
    `date_of_birth` VARCHAR(10) NOT NULL,
    `gender` ENUM('Male', 'Female') NOT NULL,
    PRIMARY KEY(`cnic`)
);

-- Inserting into evc table
INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('1234567890123', 'Emma Thompson', '1992-03-18', 'Female');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('9876543210123', 'Lily Johnson', '1988-11-25', 'Female');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('5551234567123', 'Isabella Rodriguez', '1995-07-12', 'Female');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('7890123456123', 'Logan Mitchell', '1998-01-30', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('5678901234123', 'Caleb Williams', '1993-09-08', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('1230987654123', 'Eren Yaeger', '1996-12-05', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('8765432109123', 'Aiden Walker', '1991-06-22', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('2345678901123', 'Lucas Bennett', '1989-04-14', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('6789012345123', 'Ava Thompson', '1994-10-03', 'Female');



-- Not yet Made an Account
-- Inserting into evc table
INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('3332221110000', 'John Doe', '1985-08-20', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('4445556667777', 'Jane Smith', '1990-05-12', 'Female');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('8887776665555', 'Michael Johnson', '1976-12-03', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('1112223334444', 'Emily Davis', '1982-09-15', 'Female');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('9998887776666', 'Christopher White', '1997-07-28', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('6665554443333', 'Sophia Martinez', '1989-04-02', 'Female');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('4443332221111', 'Ethan Taylor', '2000-11-08', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('7776665554444', 'Olivia Brown', '1995-03-22', 'Female');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('2221110009999', 'Matthew Miller', '1987-06-17', 'Male');

INSERT INTO evc (cnic, name, date_of_birth, gender)
VALUES ('5554443332222', 'Emma Wilson', '1993-10-31', 'Female');




/***************************************************************
 *                                                             *
 *                    SERVICESEEKER TABLE                      *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `serviceseeker` table stores information about        *
 *   service seekers, who are users looking for services on    *
 *   the platform.                                             *
 *                                                             *
 * Columns:                                                    *
 *   - `username`: User's unique username.                     *
 *   - `name`: User's full name.                               *
 *   - `date_of_birth`: User's date of birth (YYYY-MM-DD).     *
 *   - `gender`: User's gender (Male/Female).                  *
 *   - `email`: User's email address.                          *
 *   - `phone`: User's phone number.                           *
 *   - `cnic`: CNIC (Computerized National Identity Card)      *
 *              number for user identification.                *
 *   - `address`: User's residential address.                  *
 *   - `zipcode`: User's residential zipcode.                  *
 *   - `joining_date`: Date when the user joined the platform. *
 *   - `image`: URL or path to the user's profile image.       *
 *                                                             *
 * Constraints:                                                *
 *   - `username` is the primary key.                          *
 *   - `cnic` is a unique constraint and a foreign key         *
 *     referencing the `cnic` column in the `evc` table.       *
 *                                                             *
 ***************************************************************/
 
-- Serviceseeker Table
CREATE TABLE `serviceseeker` (
  `username` VARCHAR(20) PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `date_of_birth` VARCHAR(10) NOT NULL,
  `gender` ENUM('Male', 'Female') NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(10) NOT NULL,
  `cnic` VARCHAR(13) NOT NULL UNIQUE,
  `address` VARCHAR(100) NOT NULL,
  `zipcode` VARCHAR(5) NOT NULL,
  `joining_date` VARCHAR(10) NOT NULL,
  `image` VARCHAR(255),
  FOREIGN KEY (`cnic`) REFERENCES `evc`(`cnic`)
);

-- Inserting into serviceseeker table (take the image path into consideration)
INSERT INTO serviceseeker (username, name, date_of_birth, gender, email, phone, cnic, address, zipcode, joining_date, image)
VALUES
('emma_thompson', 'Emma Thompson', '1992-03-18', 'Female', 'emma.thompson@example.com', '1234567890', '1234567890123', '123 Oak St, Town', '54321', '2019-05-15', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Emma Thompson.png'),
('lily_johnson', 'Lily Johnson', '1988-11-25', 'Female', 'lily.johnson@example.com', '9876543210', '9876543210123', '456 Maple St, City', '67890', '2019-05-15', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Lily Johnson.png'),
('isabella_rodriguez', 'Isabella Rodriguez', '1995-07-12', 'Female', 'isabella.rodriguez@example.com', '5551234567', '5551234567123', '789 Pine St, Village', '13579', '2019-05-15', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Isabella Rodriguez.png'),
('logan_mitchell', 'Logan Mitchell', '1998-01-30', 'Male', 'logan.mitchell@example.com', '7890123456', '7890123456123', '101 Cedar St, City', '24680', '2019-05-15', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Logan Mitchell.png'),
('caleb_williams', 'Caleb Williams', '1993-09-08', 'Male', 'caleb.williams@example.com', '5678901234', '5678901234123', '202 Elm St, Town', '97531', '2020-05-15', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Caleb Williams.png'),
('eren_yeager', 'Eren Yeager', '1996-12-05', 'Male', 'eren.yearger@example.com', '1230987654', '1230987654123', '303 Birch St, Village', '86420', '2021-05-15', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Eren Yeager.png'),
('aiden_walker', 'Aiden Walker', '1991-06-22', 'Male', 'aiden.walker@example.com', '8765432109', '8765432109123', '404 Pine St, City', '12345', '2023-09-11', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Aiden Walker.png'),
('lucas_bennett', 'Lucas Bennett', '1989-04-14', 'Male', 'lucas.bennett@example.com', '2345678901', '2345678901123', '505 Oak St, Town', '54321', '2023-01-05', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Lucas Bennett.png'),
('ava_thompson', 'Ava Thompson', '1994-10-03', 'Female', 'ava.thompson@example.com', '6789012345', '6789012345123', '606 Maple St, City', '67890', '2023-02-25', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Seekers/Ava Thompson.png');


-- Trigger to delete all the pending and cancelled bookings of a service seeker, if the service seeker is deleted from the database
DELIMITER //
CREATE TRIGGER BeforeServiceSeekerDelete
BEFORE DELETE ON serviceseeker
FOR EACH ROW
BEGIN
    DELETE FROM booking
    WHERE seeker_username = OLD.username AND status IN('Pending', 'Cancelled');
END;
//
DELIMITER ;

-- DROP TRIGGER IF EXISTS BeforeServiceSeekerDelete;

-- A query to find the number of service seekers who joined each year, limit to last 10y
/*
SELECT YEAR(joining_date), COUNT(username) 
FROM serviceseeker
GROUP BY YEAR(joining_date)
ORDER BY YEAR(joining_date) DESC LIMIT 10;
*/


/***************************************************************
 *                                                             *
 *               LOGIN CREDENTIALS TABLE                       *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `logincredentials` table stores username-password     *
 *   pairs for authentication.                                 *
 *                                                             *
 * Columns:                                                    *
 *   - `username`: User's unique username.                     *
 *   - `password`: User's hashed password.                     *
 *                                                             *
 * Constraints:                                                *
 *   - `username` is the primary key.                          *
 *   - `username` is a foreign key referencing the `username`  *
 *     column in the `serviceseeker` table.                    *
 *                                                             *
 ***************************************************************/
 
-- LoginCredentials Table
CREATE TABLE `logincredentials` (
  `username` VARCHAR(20) PRIMARY KEY,
  `password` VARCHAR(30) NOT NULL,
  FOREIGN KEY (`username`) REFERENCES `serviceseeker`(`username`) ON DELETE CASCADE
);

-- Inserting into logincredentials table
-- regular users
INSERT INTO logincredentials (username, password) VALUES ('emma_thompson', 'password123');
INSERT INTO logincredentials (username, password) VALUES ('lily_johnson', 'password456');
INSERT INTO logincredentials (username, password) VALUES ('isabella_rodriguez', 'password789');
INSERT INTO logincredentials (username, password) VALUES ('logan_mitchell', 'password101');
INSERT INTO logincredentials (username, password) VALUES ('caleb_williams', 'password202');
INSERT INTO logincredentials (username, password) VALUES ('eren_yeager', 'password303');
INSERT INTO logincredentials (username, password) VALUES ('aiden_walker', 'password404');
INSERT INTO logincredentials (username, password) VALUES ('lucas_bennett', 'password505');
INSERT INTO logincredentials (username, password) VALUES ('ava_thompson', 'password606');


/***************************************************************
 *                                                             *
 *                      ACCOUNT TABLE                          *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `account` table stores financial information for      *
 *   users, including credit card details.                     *
 *                                                             *
 * Columns:                                                    *
 *   - `account_id`: Auto-incremented primary key.             *
 *   - `user_cnic`: CNIC of the user associated with the       *
 *                  financial account.                         *
 *   - `account_number`: Account number for transactions.      *
 *   - `credit_card_number`: Credit card number for payments.  *
 *   - `credit_card_expiry`: Expiry date of the credit card.   *
 *   - `cvv`: Card Verification Value for credit card security.*
 *   - `pin`: Personal Identification Number for transactions. *
 *   - `balance`: Current balance in the account.              *
 *                                                             *
 * Constraints:                                                *
 *   - `account_id` is the primary key.                        *
 *   - `user_cnic` is a foreign key referencing `cnic` in the  *
 *     `evc` table.                                            *
 *                                                             *
 ***************************************************************/
 
CREATE TABLE `account` (
    `account_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_cnic` VARCHAR(13) NOT NULL UNIQUE,
    `account_number` VARCHAR(20) NOT NULL,
    `credit_card_number` VARCHAR(16),
    `credit_card_expiry` DATE,
    `cvv` SMALLINT,
    `pin` INT,
    `balance` DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (`user_cnic`) REFERENCES `evc`(`cnic`) ON DELETE CASCADE
);


-- Inserting into accounts table
INSERT INTO account (user_cnic, account_number, credit_card_number, credit_card_expiry, cvv, pin, balance)
VALUES
('1234567890123', 'ACC123456789', '4111111111111111', '2025-03-31', 123, 1234, 25000.00), -- Emma Thompson
('9876543210123', 'ACC987654321', '4222222222222222', '2025-11-30', 234, 2345, 310000.00), -- Lily Johnson
('5551234567123', 'ACC555123456', '4333333333333333', '2026-07-31', 345, 3456, 47500.00), -- Isabella Rodriguez
('7890123456123', 'ACC789012345', '4444444444444444', '2027-01-31', 456, 4567, 715000.00), -- Logan Mitchell
('5678901234123', 'ACC567890123', '4555555555555555', '2027-09-30', 567, 5678, 912000.00), -- Caleb Williams
('1230987654123', 'ACC123098765', '4666666666666666', '2028-12-31', 678, 6789, 22000.00), -- Eren Yaeger
('8765432109123', 'ACC876543210', '4777777777777777', '2029-06-30', 789, 7890, 93000.00), -- Aiden Walker
('2345678901123', 'ACC234567890', '4888888888888888', '2030-04-30', 890, 8901, 94000.00), -- Lucas Bennett
('6789012345123', 'ACC678901234', '4999999999999999', '2023-10-31', 901, 9012, 15500.00); -- Ava Thompson


-- Not yet made an account
INSERT INTO account (user_cnic, account_number, credit_card_number, credit_card_expiry, cvv, pin, balance)
VALUES
('3332221110000', 'ACC333222111', '4111222233334444', '2024-08-31', 101, 1010, 26000.00), -- John Doe
('4445556667777', 'ACC444555666', '4222333344445555', '2025-05-31', 202, 2020, 38000.00), -- Jane Smith
('8887776665555', 'ACC888777666', '4333444455556666', '2029-12-31', 303, 3030, 79500.00), -- Michael Johnson
('1112223334444', 'ACC111222333', '4444555566667777', '2026-09-30', 404, 4040, 47000.00), -- Emily Davis
('9998887776666', 'ACC999888777', '4555666677778888', '2027-07-31', 505, 5050, 911000.00), -- Christopher White
('6665554443333', 'ACC666555444', '4666777788889999', '2028-04-30', 606, 6060, 313000.00), -- Sophia Martinez
('4443332221111', 'ACC444333222', '4777888899990000', '2029-11-30', 707, 7070, 814000.00), -- Ethan Taylor
('7776665554444', 'ACC777666555', '4888999900001111', '2030-03-31', 808, 8080, 216000.00), -- Olivia Brown
('2221110009999', 'ACC222111000', '4999000011112222', '2029-06-30', 909, 9090, 917000.00), -- Matthew Miller
('5554443332222', 'ACC555444333', '5000111122223333', '2030-10-31', 100, 1001, 218000.00); -- Emma Wilson



/***************************************************************
 *                                                             *
 *                    SUGGESTIONS TABLE                        *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `suggestions` table stores suggestions provided by    *
 *   service seekers. Each suggestion is associated with a     *
 *   user via the `seeker_username` column.                    *
 *                                                             *
 * Columns:                                                    *
 *   - `suggestion_id`: Auto-incremented primary key.          *
 *   - `seeker_username`: Username of the service seeker       *
 *                        providing the suggestion.            *
 *   - `comment`: The text content of the suggestion.          *
 *   - `comment_date`: Date and time when the suggestion was   *
 *                     added, with a default value of the      *
 *                     current timestamp.                      *
 *                                                             *
 * Constraints:                                                *
 *   - `suggestion_id` is the primary key.                     *
 *   - `seeker_username` is a foreign key referencing the      *
 *     `username` column in the `serviceseeker` table, with    *
 *     ON DELETE SET NULL behavior.                            *
 *                                                             *
 ***************************************************************/
 
CREATE TABLE `suggestions` (
    `suggestion_id` INT PRIMARY KEY AUTO_INCREMENT,
    `seeker_username` VARCHAR(50),
    `comment` TEXT,
    `comment_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`seeker_username`) REFERENCES serviceseeker(`username`) ON DELETE SET NULL
);

INSERT INTO suggestions (seeker_username, comment) VALUES
('emma_thompson', 'Great service!'),
('lily_johnson', 'Prompt and efficient.'),
('isabella_rodriguez', 'Satisfied with the provided services.'),
('logan_mitchell', 'Very professional.'),
('caleb_williams', 'Excellent communication.'),
('eren_yeager', 'Highly recommended.'),
('aiden_walker', 'Impressed with the quality of service.'),
('lucas_bennett', 'Quick response and reliable.'),
('ava_thompson', 'Happy with the overall experience.');

-- select * from suggestions ORDER BY comment_date DESC;



/***************************************************************
 *                                                             *
 *                SERVICE PROVIDER TABLE                       *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `serviceprovider` table stores information about      *
 *   service providers, who are users offering services on     *
 *   the platform.                                             *
 *                                                             *
 * Columns:                                                    *
 *   - `username`: User's unique username.                     *
 *   - `name`: User's full name.                               *
 *   - `status`: Status of the service provider (Active/       *
 *               Inactive).                                    *
 *   - `date_of_birth`: User's date of birth (YYYY-MM-DD).     *
 *   - `gender`: User's gender (Male/Female).                  *
 *   - `email`: User's email address.                          *
 *   - `phone`: User's phone number.                           *
 *   - `zipcode`: User's residential zipcode.                  *
 *   - `joining_date`: Date when the user joined the platform. *
 *   - `rating`: Average rating of the service provider.       *
 *   - `image`: URL or path to the user's profile image.       *
 *                                                             *
 * Constraints:                                                *
 *   - `username` is the primary key.                          *
 *                                                             *
 ***************************************************************/
 
CREATE TABLE `serviceprovider` (
    `username` VARCHAR(20) PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL,
    `status` ENUM('Active', 'Inactive') NOT NULL,
    `date_of_birth` VARCHAR(10) NOT NULL,
    `gender` ENUM('Male', 'Female') NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `phone` VARCHAR(10) NOT NULL,
    `zipcode` VARCHAR(5) NOT NULL,
    `joining_date` VARCHAR(10) NOT NULL,
    `rating` DECIMAL(2, 1) NOT NULL,
    `image` VARCHAR(255)
);

-- Insertions for the serviceprovider table with usernames (take the image path into consideration)
INSERT INTO serviceprovider (username, name, status, date_of_birth, gender, email, phone, zipcode, joining_date, rating, image)
VALUES
('john_smith', 'John Smith', 'Active', '1980-05-15', 'Male', 'john.smith@example.com', '1234567890', '54321', '2011-05-15', 4.5, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/John Smith.png'),
('emily_johnson', 'Emily Johnson', 'Active', '1992-09-28', 'Female', 'emily.johnson@example.com', '9876543210', '67890', '2021-05-15', 4.8, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Emily Johnson.png'),
('michael_brown', 'Michael Brown', 'Active', '1985-03-12', 'Male', 'michael.brown@example.com', '5678901234', '67890', '2018-05-15', '4.2', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Michael Brown.png'),
('samantha_davis', 'Samantha Davis', 'Active', '1990-11-07', 'Female', 'samantha.davis@example.com', '2345678901', '24680', '2016-05-15', 4.6, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Samantha Davis.png'),
('david_wilson', 'David Wilson', 'Active', '1988-07-23', 'Male', 'david.wilson@example.com', '3456789012', '97531', '2019-05-15', '4.4', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/David Wilson.png'),
('alex_taylor', 'Alex Taylor', 'Active', '1975-04-20', 'Male', 'alex.taylor@services.com', '5551010101', '12345', '2019-08-10', 4.7, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Alex Taylor.png'),
('maria_gonzalez', 'Maria Gonzalez', 'Active', '1983-12-05', 'Female', 'maria.gonzalez@services.com', '5552020202', '23456', '2020-03-15', 4.9, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Maria Gonzalez.png'),
('liam_nguyen', 'Liam Nguyen', 'Active', '1990-09-30', 'Male', 'liam.nguyen@services.com', '5553030303', '34567', '2021-07-20', 4.6, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Liam Nguyen.png'),
('nora_khan', 'Nora Khan', 'Active', '1978-06-15', 'Female', 'nora.khan@services.com', '5554040404', '45678', '2021-11-11', 4.8, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Nora Khan.png'),
('ethan_lee', 'Ethan Lee', 'Active', '1982-01-22', 'Male', 'ethan.lee@services.com', '5555050505', '56789', '2021-02-28', 4.5, 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Service Providers/Ethan Lee.png');


-- Trigger to delete all the pending and cancelled bookings done by a service provider, if the service provider is deleted from the database
DELIMITER //
CREATE TRIGGER BeforeServiceProviderDelete
BEFORE DELETE ON serviceprovider
FOR EACH ROW
BEGIN
    DELETE FROM booking
    WHERE provider_username = OLD.username AND status IN('Pending', 'Cancelled');
END;
//
DELIMITER ;


-- DROP TRIGGER IF EXISTS BeforeServiceProviderDelete;

-- A query to find the number of service seekers who joined each year, limit to last 10y
/*
SELECT YEAR(joining_date), COUNT(username) 
FROM serviceprovider
GROUP BY YEAR(joining_date)
ORDER BY YEAR(joining_date) DESC LIMIT 10;
*/



/***************************************************************
 *                                                             *
 *                   CATEGORIES TABLE                          *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `categories` table stores different service           *
 *   categories offered on the platform.                       *
 *                                                             *
 * Columns:                                                    *
 *   - `id`: Category identifier.                              *
 *   - `name`: Name of the service category.                   *
 *   - `price`: Price associated with the service category.    *
 *                                                             *
 * Constraints:                                                *
 *   - `id` is the primary key.                                *
 *                                                             *
 ***************************************************************/
 
-- Create the 'categories' table
CREATE TABLE `categories` (
    `id` INT PRIMARY KEY,
    `name` VARCHAR(255),
    `price` DECIMAL(5, 2) -- New 'price' attribute
);


-- Insertions for the categories with category id and random prices between $50 and $100
INSERT INTO categories (id, name, price) VALUES
(1, 'Plumber', ROUND(RAND() * (100 - 50) + 50, 2)),
(2, 'Electrician', ROUND(RAND() * (100 - 50) + 50, 2)),
(3, 'Roofer', ROUND(RAND() * (100 - 50) + 50, 2)),
(4, 'Builder', ROUND(RAND() * (100 - 50) + 50, 2)),
(5, 'Gardener', ROUND(RAND() * (100 - 50) + 50, 2)),
(6, 'Painter', ROUND(RAND() * (100 - 50) + 50, 2)),
(7, 'Landscaper', ROUND(RAND() * (100 - 50) + 50, 2)),
(8, 'Carpenter', ROUND(RAND() * (100 - 50) + 50, 2)),
(9, 'Plasterer', ROUND(RAND() * (100 - 50) + 50, 2)),
(10, 'Driveways', ROUND(RAND() * (100 - 50) + 50, 2)),
(11, 'Fencing', ROUND(RAND() * (100 - 50) + 50, 2)),
(12, 'Tree surgeon', ROUND(RAND() * (100 - 50) + 50, 2)),
(13, 'Handyman', ROUND(RAND() * (100 - 50) + 50, 2)),
(14, 'Locksmith', ROUND(RAND() * (100 - 50) + 50, 2)),
(15, 'Bathrooms', ROUND(RAND() * (100 - 50) + 50, 2)),
(16, 'Tiler', ROUND(RAND() * (100 - 50) + 50, 2)),
(17, 'Central heating', ROUND(RAND() * (100 - 50) + 50, 2)),
(18, 'Boiler repair', ROUND(RAND() * (100 - 50) + 50, 2));



/***************************************************************
 *                                                             *
 *             PROVIDER CATEGORIES TABLE                       *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `provider_categories` table associates service        *
 *   providers with the categories they offer.                 *
 *                                                             *
 * Columns:                                                    *
 *   - `provider_username`: Username of the service provider.  *
 *   - `category_id`: Identifier of the service category.      *
 *                                                             *
 * Constraints:                                                *
 *   - Composite primary key on (`provider_username`,          *
 *     `category_id`).                                         *
 *   - Foreign keys referencing `username` in                  *
 *     `serviceprovider` and `id` in `categories`.             *
 *                                                             *
 ***************************************************************/

-- A service provider can have atleast 1 and atmost 3 categories
CREATE TABLE `provider_categories` (
    `provider_username` VARCHAR(20),
    `category_id` INT,
    PRIMARY KEY (`provider_username`, `category_id`),
    FOREIGN KEY (`provider_username`) REFERENCES serviceprovider(`username`) ON DELETE CASCADE,
    FOREIGN KEY (`category_id`) REFERENCES categories(`id`) ON DELETE CASCADE
);

-- Insertions for the provider_categories table with usernames and category id
-- John Smith is a plumber and electrician
INSERT INTO provider_categories (provider_username, category_id) VALUES
('john_smith', 1), -- plumber
('john_smith', 2); -- electrician

-- Emily Johnson is an electrician and tiler
INSERT INTO provider_categories (provider_username, category_id) VALUES
('emily_johnson', 2), -- electrician
('emily_johnson', 16); -- tiler

-- Michael Brown is a roofer and builder
INSERT INTO provider_categories (provider_username, category_id) VALUES
('michael_brown', 3), -- roofer
('michael_brown', 4); -- builder

-- Samantha Davis is a builder and landscaper
INSERT INTO provider_categories (provider_username, category_id) VALUES
('samantha_davis', 4), -- builder
('samantha_davis', 7); -- landscaper

-- David Wilson is a gardener and landscaper
INSERT INTO provider_categories (provider_username, category_id) VALUES
('david_wilson', 5), -- gardener
('david_wilson', 7); -- landscaper

-- Alex Taylor is a plumber, electrician, and central heating
INSERT INTO provider_categories (provider_username, category_id) VALUES
('alex_taylor', 1), -- plumber
('alex_taylor', 2), -- electrician
('alex_taylor', 17); -- Central heating

-- Maria Gonzalez is a gardener, landscaper, and tree surgeon
INSERT INTO provider_categories (provider_username, category_id) VALUES
('maria_gonzalez', 5), -- gardener
('maria_gonzalez', 7), -- landscaper
('maria_gonzalez', 12); -- tree surgeon

-- Maria Gonzalez is a builder, carpenter, and plasterer
INSERT INTO provider_categories (provider_username, category_id) VALUES
('liam_nguyen', 4), -- builder
('liam_nguyen', 8), -- carpenter
('liam_nguyen', 9); -- plasterer

-- Nora Khan is a painter, handyman, and tiler
INSERT INTO provider_categories (provider_username, category_id) VALUES
('nora_khan', 6), -- painter
('nora_khan', 13), -- handyman
('nora_khan', 16); -- tiler

-- Ethan Lee is a locksmith, driveways, and fencing
INSERT INTO provider_categories (provider_username, category_id) VALUES
('ethan_lee', 14), -- locksmith
('ethan_lee', 10), -- driveways
('ethan_lee', 11); -- fencing



-- A query that displays the roles(categories) of the service providers
/*
SELECT sp.username AS provider_username, c.name AS category_name
FROM serviceprovider sp
INNER JOIN provider_categories pc ON sp.username = pc.provider_username
INNER JOIN categories c ON pc.category_id = c.id;
*/


-- A query that displays the roles of a specific service provider, directly from the provdier_categories table
/*
SELECT c.id, c.name
                FROM provider_categories pc  
                JOIN categories c ON pc.category_id = c.id 
                WHERE pc.provider_username = "david_wilson";
*/


/***************************************************************
 *                                                             *
 *                      BOOKING TABLE                          *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `booking` table stores information about service      *
 *   bookings made by service seekers.                         *
 *                                                             *
 * Columns:                                                    *
 *   - `booking_id`: Auto-incremented primary key.             *
 *   - `seeker_username`: Username of the service seeker.      *
 *   - `provider_username`: Username of the service provider.  *
 *   - `booking_date`: Date and time when the booking was made.*
 *   - `service_type_id`: Identifier of the booked service.    *
 *   - `status`: Status of the booking (Pending/Completed/     *
 *               Cancelled).                                   *
 *   - `description`: Additional information about the booking.*
 *   - `image`: URL or path to an image related to the booking.*
 *                                                             *
 * Constraints:                                                *
 *   - `booking_id` is the primary key.                        *
 *   - `seeker_username` and `provider_username` are foreign   *
 *     keys referencing `username` in `serviceseeker` and      *
 *     `serviceprovider` tables, respectively.                 *
 *   - `service_type_id` is a foreign key referencing `id` in  *
 *     the `categories` table.                                 *
 *                                                             *
 ***************************************************************/
 
CREATE TABLE `booking` (
    `booking_id` INT PRIMARY KEY,
    `seeker_username` VARCHAR(20),
    `provider_username` VARCHAR(20),
    `booking_date` DATETIME NOT NULL,
    `service_type_id` INT, 
    `status` ENUM('Pending', 'Completed', 'Cancelled') NOT NULL DEFAULT 'Pending',
    `description` TEXT,
	`image` VARCHAR(255),
    FOREIGN KEY (`seeker_username`) REFERENCES `serviceseeker`(`username`) ON DELETE SET NULL,
    FOREIGN KEY (`provider_username`) REFERENCES `serviceprovider`(`username`) ON DELETE SET NULL,
    FOREIGN KEY (`service_type_id`) REFERENCES `categories`(`id`) ON DELETE CASCADE
);

-- Date format: year, month, day
INSERT INTO booking (booking_id, seeker_username, provider_username, booking_date, service_type_id, status, description, image)
VALUES
(1, 'aiden_walker', 'john_smith', '2023-01-11 10:00:00', 1, 'Completed', 'Meeting for consultation.', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Issues/Plumbing.png'),
(2, 'aiden_walker', 'emily_johnson', '2023-01-11 13:00:00', 2, 'Completed', 'Fitness training session.', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Issues/Electric.png'),
(3, 'ava_thompson', 'samantha_davis', '2023-12-08 09:30:00', 3, 'Pending', 'Appointment canceled by seeker.', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Issues/Roofing.png'),
(4, 'aiden_walker', 'samantha_davis', '2023-02-11 11:00:00', 1, 'Cancelled', 'Regular checkup and service.', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Issues/Plumbing_2.png'),
(5, 'ava_thompson', 'samantha_davis', '2023-02-11 11:00:00', 1, 'Completed', 'Regular checkup and service.', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Issues/Plumbing_2.png'),
(6, 'ava_thompson', 'samantha_davis', '2023-12-06 11:00:00', 1, 'Completed', 'Regular checkup and service.', 'D:\\IntellijProjects\\LSD/src/main/resources/Images/Issues/Plumbing_2.png');


-- A query that returns the unavailable slots of a service provider
-- SELECT TIME(booking_date) as slot_time FROM booking WHERE provider_username = "samantha_davis" AND DATE(booking_date) = '2023-12-01';

-- A query that returns all the bookings done, limit 10
-- SELECT DATE(booking_date) as dateOnly, status, COUNT(booking_id) FROM booking GROUP BY dateOnly, status ORDER BY dateOnly DESC LIMIT 10;

-- A query that counts the number of bookings done each day, limit 10
/*
SELECT DATE(booking_date), COUNT(booking_id)
FROM booking
GROUP BY DATE(booking_date)
ORDER BY DATE(booking_date) DESC LIMIT 10;
*/

-- A query that counts the number of Bookings placed today
-- SELECT COUNT(*) FROM booking WHERE DATE(booking_date) = CURDATE();


-- A query that Counts the Bookings based on status of a specific service provider
-- SELECT status, COUNT(*) AS count FROM booking WHERE provider_username = 'samantha_davis' GROUP BY status;


/***************************************************************
 *                                                             *
 *                      PAYMENT TABLE                          *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `payment` table stores payment information for        *
 *   service bookings made by service seekers.                 *
 *                                                             *
 * Columns:                                                    *
 *   - `payment_id`: Auto-incremented primary key.             *
 *   - `booking_id`: Identifier of the related booking.        *
 *   - `amount`: Amount paid for the service booking.          *
 *   - `payment_date`: Date and time when the payment was made.*
 *   - `payment_method`: Method of payment (Card/Cash).        *
 *                                                             *
 * Constraints:                                                *
 *   - `payment_id` is the primary key.                        *
 *   - `booking_id` is a foreign key referencing `booking_id`  *
 *     in the `booking` table.                                 *
 *                                                             *
 ***************************************************************/
 
CREATE TABLE `payment` (
    `payment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `booking_id` INT,
    `amount` DECIMAL(10, 2) NOT NULL,
    `payment_date` DATETIME NOT NULL,
    `payment_method` ENUM('Card', 'Cash') NOT NULL,
    FOREIGN KEY (`booking_id`) REFERENCES `booking`(`booking_id`) ON DELETE CASCADE
);


INSERT INTO payment (booking_id, amount, payment_date, payment_method)
VALUES
(1, 100.00, '2023-01-11 12:00:00', 'Card'),
(2, 150.00, '2023-01-11 13:00:00', 'Card'),
(5, 150.00, '2023-02-11 13:00:00', 'Card');


-- A query that sums the amount made by bookings done each day, limit 10
/* SELECT DATE(booking_date), SUM(p.amount) 
FROM booking b INNER JOIN payment p
ON b.booking_id = p.booking_id
GROUP BY DATE(b.booking_date)
ORDER BY DATE(b.booking_date) DESC LIMIT 10;
*/


/***************************************************************
 *                                                             *
 *                      REVIEW TABLE                           *
 *                                                             *
 ***************************************************************
 * Description:                                                *
 *   The `review` table stores reviews/ratings for service     *
 *   bookings made by service seekers.                         *
 *                                                             *
 * Columns:                                                    *
 *   - `review_id`: Auto-incremented primary key.              *
 *   - `booking_id`: Identifier of the related booking.        *
 *   - `rating`: Rating given by the service seeker.           *
 *                                                             *
 * Constraints:                                                *
 *   - `review_id` is the primary key.                         *
 *   - `booking_id` is a foreign key referencing `booking_id`  *
 *     in the `booking` table.                                 *
 *   - `rating` is a decimal value between 0.0 and 5.0.        *
 *                                                             *
 ***************************************************************/
 
CREATE TABLE `review` (
    `review_id` INT AUTO_INCREMENT PRIMARY KEY,
    `booking_id` INT,
    `rating` DECIMAL(2, 1) CHECK (rating >= 0.0 AND rating <= 5.0),
    FOREIGN KEY (`booking_id`) REFERENCES `booking`(`booking_id`) ON DELETE CASCADE
);


INSERT INTO review (booking_id, rating)
VALUES
(1, 4.5),
(2, 4.2),
(5, 4.1),
(6, 4.4);



-- A query that displays the rating given by service seekers to the service providers
/*
SELECT 
    ss.name AS service_seeker_name, 
    sp.name AS service_provider_name, 
    r.rating AS rating_given
FROM booking b JOIN serviceseeker ss ON b.seeker_username = ss.username
JOIN serviceprovider sp ON b.provider_username = sp.username
JOIN review r ON b.booking_id = r.booking_id;
*/


-- A query that displays the total no. of ratings received by service providers
-- LEFT JOIN is used to include all service providers, even those who haven't received any ratings.
/*
SELECT 
    sp.username, 
    COUNT(r.review_id) AS number_of_ratings
FROM serviceprovider sp LEFT JOIN booking b ON sp.username = b.provider_username
LEFT JOIN review r ON b.booking_id = r.booking_id
GROUP BY 
    sp.username, sp.name;
*/








