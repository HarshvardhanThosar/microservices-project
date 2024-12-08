CREATE TABLE IF NOT EXISTS PRODUCT
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL
(
    10,
    2
) NOT NULL,
    active BOOLEAN NOT NULL,
    deleted BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


INSERT INTO product (name, description, price, active, deleted, created_at, updated_at)
VALUES ('Apple iPhone 14', 'The latest iPhone with advanced camera features and A16 Bionic chip.', 999.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Samsung Galaxy S23', 'A premium Android smartphone with a powerful Snapdragon processor.', 899.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Google Pixel 8', 'A phone with Google Tensor chip and excellent photography capabilities.', 799.99, TRUE,
        FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Dell XPS 15', 'A high-performance laptop with a 15-inch 4K display.', 1799.99, TRUE, FALSE, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('HP Envy x360', 'A 2-in-1 convertible laptop for versatile work and play.', 1199.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Sony WH-1000XM5', 'Noise-cancelling headphones with superior audio quality.', 399.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('JBL Charge 5 Speaker', 'A waterproof portable speaker with excellent bass.', 179.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Logitech MX Master 3', 'An ergonomic mouse with customizable buttons.', 99.99, TRUE, FALSE, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('Asus ROG Zephyrus G14', 'A compact gaming laptop with AMD Ryzen and RTX graphics.', 1699.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Samsung 55" 4K TV', 'A UHD Smart TV with stunning visuals and HDR support.', 599.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Canon EOS R6 Camera', 'A mirrorless camera with high-speed performance and 4K recording.', 2499.99, TRUE,
        FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Dyson V15 Vacuum', 'A powerful cordless vacuum cleaner with advanced cleaning features.', 749.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Bose SoundLink Headphones', 'Comfortable wireless headphones with deep bass.', 249.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Nike Air Max 270', 'Stylish sneakers with comfortable cushioning.', 129.99, TRUE, FALSE, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('Adidas Ultraboost 21', 'High-performance running shoes with energy return.', 159.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Garmin Forerunner 955', 'A premium smartwatch for fitness enthusiasts.', 499.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Apple Watch Series 8', 'A smartwatch with advanced health monitoring features.', 399.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Samsung Galaxy Watch 6', 'A sleek smartwatch with fitness tracking and LTE connectivity.', 349.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Sony PS5 Console', 'The latest PlayStation with immersive gaming experience.', 499.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Xbox Series X', 'A powerful gaming console with 4K and HDR capabilities.', 499.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERT INTO inventory (quantity, product_id, active, deleted, created_at, updated_at) VALUES
--                                                                                           (100, 1, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (120, 2, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (80, 3, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (50, 4, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (60, 5, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (150, 6, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (90, 7, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (200, 8, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (40, 9, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (70, 10, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (30, 11, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (110, 12, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (130, 13, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (220, 14, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (180, 15, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (90, 16, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (75, 17, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (65, 18, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (20, 19, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--                                                                                           (25, 20, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);