DROP TABLE IF EXISTS USER CASCADE;

CREATE TABLE USER (
                      id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
                      intra_id        BIGINT          NOT NULL,
                      username        VARCHAR(255)    NOT NULL,
                      card_number     SMALLINT        UNIQUE,
                      check_in        DATETIME        ,
                      check_out       DATETIME
);