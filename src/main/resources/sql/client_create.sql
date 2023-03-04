DROP TABLE IF EXISTS client CASCADE;

CREATE TABLE client (
    client_id SERIAL PRIMARY KEY,
    full_name text NOT NULL,
    email email_address NOT NULL,
    phone phone_no
);
