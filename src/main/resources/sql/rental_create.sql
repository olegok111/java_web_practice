DROP TABLE IF EXISTS rental CASCADE;

CREATE TABLE rental (
    rental_id SERIAL PRIMARY KEY,
    film_id SERIAL REFERENCES film(film_id),
    client_id SERIAL REFERENCES client(client_id),
    rent_or_purchase rental_method NOT NULL,
    start_time timestamp NOT NULL,
    end_time timestamp,
    price int NOT NULL
);