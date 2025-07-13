CREATE TABLE restaurant (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address_id CHAR(36) NOT NULL,
    cuisine_type_id CHAR(36) NOT NULL,
    opening_hours VARCHAR(500) NOT NULL,
    owner_id CHAR(36) NOT NULL,

    CONSTRAINT fk_restaurant_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_restaurant_cuisine_type FOREIGN KEY (cuisine_type_id) REFERENCES cuisine_type(id),
    CONSTRAINT fk_restaurant_owner FOREIGN KEY (owner_id) REFERENCES user(id),
    CONSTRAINT uk_restaurant_name UNIQUE (name)
);