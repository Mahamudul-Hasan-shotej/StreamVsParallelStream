
CREATE TABLE fruit (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       quantity INT NOT NULL,
                       price DECIMAL(10,2) NOT NULL
);
INSERT INTO fruit (name, quantity, price)
SELECT
    'Fruit_' || gs AS name,
    (random() * 100)::int AS quantity,
    round((random() * 1000)::numeric, 2) AS price
FROM generate_series(1, 700000) AS gs;

truncate fruit