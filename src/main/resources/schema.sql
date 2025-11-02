DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    descr VARCHAR(255),
    age INT,
    course VARCHAR(255),
    password VARCHAR(255),
    dataCreated DATETIME,
    dataUpdated DATETIME
);
