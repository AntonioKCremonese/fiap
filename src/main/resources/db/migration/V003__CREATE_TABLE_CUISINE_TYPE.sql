CREATE TABLE cuisine_type (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,

    CONSTRAINT uk_cuisine_type_name UNIQUE (name)
);