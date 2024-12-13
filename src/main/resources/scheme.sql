SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id                BIGINT PRIMARY KEY,
    email             VARCHAR(50)                          NOT NULL UNIQUE,
    password          VARCHAR(255)                         NOT NULL,
    phone_number      VARCHAR(20)                          NOT NULL UNIQUE,
    role              ENUM ('ADMIN','OWNER','USER')        NOT NULL,
    status            ENUM ('ACTIVE','DELETED','INACTIVE') NOT NULL,
    last_sign_in_time DATETIME(6)                          NOT NULL,
    created_at        DATETIME(6)                          NOT NULL,
    updated_at        DATETIME(6)                          NOT NULL
) ENGINE = InnoDB;

DROP TABLE IF EXISTS device;

CREATE TABLE device
(
    id         BIGINT PRIMARY KEY,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE = InnoDB;

SET FOREIGN_KEY_CHECKS = 1;
