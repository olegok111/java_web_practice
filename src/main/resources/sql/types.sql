DROP DOMAIN IF EXISTS email_address, phone_no CASCADE;
DROP TYPE IF EXISTS rental_method CASCADE;

CREATE DOMAIN email_address AS text
CHECK (VALUE ~ '^[^@]+@[^@]+\..{2,}$');

CREATE DOMAIN phone_no AS text
CHECK (VALUE ~ '^\+?[1-9]\d{1,14}$');

CREATE TYPE rental_method
AS ENUM ('rent', 'purchase');