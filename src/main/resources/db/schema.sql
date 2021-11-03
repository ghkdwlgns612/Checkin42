DROP TABLE IF EXISTS USER CASCADE;

CREATE TABLE USER (
                      id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
                      intra_id        BIGINT          NOT NULL,
                      username        VARCHAR(255)    NOT NULL,
                      card_number     INTEGER        UNIQUE,
                      check_in        DATETIME        DEFAULT NOW(),
                      check_out       DATETIME
);