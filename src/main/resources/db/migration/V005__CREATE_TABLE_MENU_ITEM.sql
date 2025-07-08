CREATE TABLE menu_item (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NULL,
    price DECIMAL(10,2) NOT NULL,
    available_for_dine_in_only BOOLEAN NOT NULL,
    photo_path VARCHAR(500),
    restaurant_id CHAR(36) NOT NULL,

    CONSTRAINT fk_menu_item_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);