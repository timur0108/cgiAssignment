package com.microproject.cgibackend.repository;

import com.microproject.cgibackend.DTO.FlightBookingDTO;
import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
    SELECT DISTINCT f.departureCity FROM Flight f
""")
    List<String> findAllDepartureCities();

    @Query("""
    SELECT DISTINCT f.arrivalCity FROM Flight f
""")
    List<String> findAllArrivalCities();

    @Query("""
    SELECT new com.microproject.cgibackend.DTO.FlightBookingDTO (
        f.id, f.flightNumber, f.departureCity, f.arrivalCity, f.departureDate, f.departureTime,
        f.arrivalDate, f.arrivalTime, f.flightDuration
    )
    FROM Flight f
    WHERE f.id = :id
""")
    Optional<FlightBookingDTO> findFlightById(Long id);

    @Query("""
    SELECT new com.microproject.cgibackend.DTO.FlightDTO(
        f.id, f.flightNumber, f.departureCity, 
        f.arrivalCity, f.departureDate, f.departureTime, 
        f.arrivalDate, f.arrivalTime, s.price, f.flightDuration)
    FROM Flight f
    JOIN f.seats s
    
    WHERE (:departureCity IS NULL OR f.departureCity = :departureCity)
    AND (:arrivalCity IS NULL OR f.arrivalCity = :arrivalCity)  
    AND (TRUE = :#{#departureDate == null} OR f.departureDate = :departureDate)
    AND (TRUE = :#{#arrivalDate == null} OR f.arrivalDate = :arrivalDate)
    AND (:minPrice IS NULL OR s.price >= :minPrice)
    AND (:maxPrice IS NULL OR s.price <= :maxPrice)
    AND (:seatClass IS NULL OR s.classType = :seatClass)
    AND (:maxDuration IS NULL OR f.flightDuration <= :maxDuration)
    GROUP BY f.id, s.price
""")
    Page<FlightDTO> findFlightsBySeatClass(String departureCity, String arrivalCity, LocalDate departureDate,
                                           LocalDate arrivalDate, BigDecimal minPrice, BigDecimal maxPrice,
                                           String seatClass, Long maxDuration, Pageable pageable);
}
