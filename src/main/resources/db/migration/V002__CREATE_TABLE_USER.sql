CREATE TABLE `user` (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(125) NOT NULL,
    mail VARCHAR(200) NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_type VARCHAR(30) NOT NULL,
    address_id CHAR(36) NULL,

    CONSTRAINT fk_user_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT uk_user_login UNIQUE (login),
    CONSTRAINT uk_user_mail UNIQUE (mail),
    CONSTRAINT uk_user_address UNIQUE (address_id)
);