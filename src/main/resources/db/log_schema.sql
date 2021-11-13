DROP TABLE IF EXISTS LOG CASCADE;

CREATE TABLE LOG (
                     id          BIGINT PRIMARY KEY AUTO_INCREMENT,
                     username    VARCHAR(255) NOT NULL,
                     card_number INTEGER,
                     type        VARCHAR(255) NOT NULL,
                     location    VARCHAR(255),
                     check_in        DATETIME,
                     check_out       DATETIME
);