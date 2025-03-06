package com.microproject.cgibackend.repository;

import com.microproject.cgibackend.DTO.AvailableSeatsDTO;
import com.microproject.cgibackend.DTO.ClassPricesDTO;
import com.microproject.cgibackend.entity.Flight;
import com.microproject.cgibackend.entity.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {


    List<Seat> findAll();

    @Query("SELECT COUNT(s) FROM Seat s WHERE s.flight.id = :flightId AND s.isAvailable = true")
    Long countAllAvailableByFlightId(@Param("flightId") Long flightId);

    @Query("""
    SELECT new com.microproject.cgibackend.DTO.ClassPricesDTO(
        MAX(CASE WHEN s.classType = 'Tourist class' THEN s.price ELSE NULL END),
        MAX(CASE WHEN s.classType = 'Business class' THEN s.price ELSE NULL END),
        MAX(CASE WHEN s.classType = 'First class' THEN s.price ELSE NULL END)
    )
    FROM Seat s
    WHERE s.flight.id = :id
""")
    ClassPricesDTO findClassPricesByFlightId(Long id);

    @Query("""
    SELECT 
        new com.microproject.cgibackend.DTO.AvailableSeatsDTO(
        COUNT(CASE WHEN s.classType = 'Tourist class' AND s.isAvailable = true THEN 1 ELSE NULL END),
        COUNT(CASE WHEN s.classType = 'Business class' AND s.isAvailable = true THEN 1 ELSE NULL END),
        COUNT(CASE WHEN s.classType = 'First class' AND s.isAvailable = true THEN 1 ELSE NULL END)
    )    
    FROM Seat s
    WHERE s.flight.id = :id
""")
    AvailableSeatsDTO findAvailableSeatsNumberForEveryClassByFlightId(Long id);
}
