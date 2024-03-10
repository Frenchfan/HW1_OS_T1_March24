SET search_path TO hw1;

INSERT INTO categories (name)
VALUES ('Food'),
       ('Clothes'),
       ('Appliances');

INSERT INTO products (name, description, price, category_id, rating, review)
VALUES ('Apple', 'Red apple', 10.00, 1, 5, 'Good'),
       ('T-Shirt', 'Green t-shirt', 5.15, 2, 4, 'Comfy'),
       ('Television', 'LG 42 inch', 500.00, 3, 5, 'Good'),
       ('Pear', 'White pear', 12.25, 1, 4, 'Not bad'),
       ('Sweater', 'Blue t-shirt', 20.00, 2, 5, 'Excellent'),
       ('Mobile', 'Samsung S23', 800.00, 3, 5, 'Not bad'),
       ('Orange', 'Red apple', 3.12, 1, 5, 'Tasty'),
       ('Pants', 'deep blue jeans', 23.00, 2, 4, 'Good quality'),
       ('Router', 'Huawei router', 120.00, 3, 5, 'Excellent connection');
