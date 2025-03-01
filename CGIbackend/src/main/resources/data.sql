INSERT INTO flight (flight_number, departure_city, arrival_city, departure_date, departure_time, arrival_date, arrival_time, price)
VALUES
    ('FL123', 'New York', 'Los Angeles', '2025-03-01', '08:30:00', '2025-03-01', '11:00:00', 350.00),
    ('FL456', 'London', 'Paris', '2025-03-02', '10:00:00', '2025-03-02', '12:00:00', 150.00),
    ('FL789', 'Tokyo', 'Sydney', '2025-03-05', '13:00:00', '2025-03-05', '20:00:00', 700.00),
    ('FL101', 'Dubai', 'Singapore', '2025-03-10', '06:00:00', '2025-03-10', '12:00:00', 500.00),
    ('FL202', 'Mumbai', 'Dubai', '2025-03-15', '15:00:00', '2025-03-15', '18:30:00', 300.00)
    ON CONFLICT (flight_number) DO NOTHING;
