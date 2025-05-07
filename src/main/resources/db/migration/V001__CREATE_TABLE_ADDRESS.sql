CREATE TABLE address (
    id CHAR(36) NOT NULL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    complement VARCHAR(255) NULL,
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    country VARCHAR(70) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    reference VARCHAR(100) NULL,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);