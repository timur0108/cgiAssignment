package com.microproject.cgibackend.repository;

import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
    SELECT new com.microproject.cgibackend.DTO.FlightDTO(
        f.id, f.flightNumber, f.departureCity, 
        f.arrivalCity, f.departureDate, f.departureTime, 
        f.arrivalDate, f.arrivalTime, f.price, 
        COUNT(s.id)) 
    FROM Flight f
    LEFT JOIN f.seats s
    WHERE (s.isAvailable = true OR s IS NULL)  
    GROUP BY f.id, f.flightNumber, f.departureCity, f.arrivalCity,
             f.departureDate, f.departureTime, f.arrivalDate, f.arrivalTime, f.price
""")
    List<FlightDTO> findAllFlightsAsDTO();

}
