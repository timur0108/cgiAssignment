package com.microproject.cgibackend.repository;

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
}
