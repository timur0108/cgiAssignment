CREATE TABLE IF NOT EXISTS flight (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    flight_number VARCHAR(50) UNIQUE NOT NULL,
    departure_city VARCHAR(100) NOT NULL,
    arrival_city VARCHAR(100) NOT NULL,
    departure_date DATE NOT NULL,
    departure_time TIME NOT NULL,
    arrival_date DATE NOT NULL,
    arrival_time TIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS seat (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    flight_id BIGINT NOT NULL REFERENCES flight(id) ON DELETE CASCADE,
    seat_number VARCHAR(10) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    class_type VARCHAR(20) NOT NULL CHECK (class_type in ('First class', 'Business class', 'Tourist class')),
    UNIQUE (flight_id, seat_number)
);

CREATE TABLE IF NOT EXISTS passenger (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    email varchar(100) UNIQUE NOT NULL,
    phone varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    seat_id BIGINT NOT NULL UNIQUE REFERENCES seat(id) ON DELETE CASCADE ,
    passenger_id BIGINT NOT NULL REFERENCES passenger(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION create_seats_for_flight()
    RETURNS TRIGGER AS '
DECLARE
    row_num INT;
    seat_letter CHAR(1);
    class_type VARCHAR(20);
    is_available BOOLEAN;
BEGIN
    FOR row_num IN 1..30 LOOP
        IF row_num <= 5 THEN
            class_type := ''First class'';
        ELSIF row_num <= 15 THEN
            class_type := ''Business class'';
        ELSE
            class_type := ''Tourist class'';
        END IF;

        FOR seat_letter IN SELECT chr(i) FROM generate_series(65, 70) AS i LOOP
            is_available := (random() > 0.2);
            INSERT INTO seat (flight_id, seat_number, is_available, class_type)
            VALUES (
                NEW.id,
                CONCAT(row_num, seat_letter),
                is_available,
                class_type
            );
            END LOOP;
    END LOOP;
    RETURN NEW;
END;
' LANGUAGE plpgsql;

DO '
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = ''flight_after_insert'') THEN
            CREATE TRIGGER flight_after_insert
                AFTER INSERT ON flight
                FOR EACH ROW
            EXECUTE FUNCTION create_seats_for_flight();
        END IF;
    END ';





