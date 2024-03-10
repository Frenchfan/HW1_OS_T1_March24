SET search_path TO hw1;

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description VARCHAR(255) DEFAULT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    category_id BIGINT       DEFAULT NULL,
    rating      SMALLINT     DEFAULT NULL,
    review      VARCHAR(255) DEFAULT NULL,
    CONSTRAINT fk_products_categories
        FOREIGN KEY (category_id) REFERENCES categories (id)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);

