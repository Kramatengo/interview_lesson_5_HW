BEGIN;

DROP TABLE IF EXISTS student CASCADE;
CREATE TABLE IF NOT EXISTS student (id serial , name VARCHAR(255), mark int, PRIMARY KEY (id));
INSERT INTO student (name, mark) VALUES ('Bob', 5), ('Bill', 4), ('Steve', 3);

COMMIT;