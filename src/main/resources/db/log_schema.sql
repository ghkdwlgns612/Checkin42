DROP TABLE IF EXISTS LOG CASCADE;

CREATE TABLE LOG (
                     id          BIGINT PRIMARY KEY AUTO_INCREMENT,
                     username    VARCHAR(255) NOT NULL,
                     card_number INTEGER UNIQUE,
                     type        VARCHAR(255) NOT NULL,
                     check_in        DATETIME,
                     check_out       DATETIME
);