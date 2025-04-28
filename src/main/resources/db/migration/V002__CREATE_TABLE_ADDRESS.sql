CREATE TABLE Address(
    id CHAR(36) NOT NULL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    complement VARCHAR(255),
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(70),
    postal_code VARCHAR(20),
    reference VARCHAR(100),
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);