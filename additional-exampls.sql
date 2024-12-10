INSERT INTO product (name, description, price, active, deleted, created_at, updated_at)
       ('Apple Watch Series 8', 'A smartwatch with advanced health monitoring features.', 399.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Samsung Galaxy Watch 6', 'A sleek smartwatch with fitness tracking and LTE connectivity.', 349.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Sony PS5 Console', 'The latest PlayStation with immersive gaming experience.', 499.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
       ('Xbox Series X', 'A powerful gaming console with 4K and HDR capabilities.', 499.99, TRUE, FALSE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO inventory (quantity, product_id, active, deleted, created_at, updated_at) VALUES
                                                                                          (75, 17, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                          (65, 18, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                          (20, 19, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                          (25, 20, TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);