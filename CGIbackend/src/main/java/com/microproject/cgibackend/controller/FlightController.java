package com.microproject.cgibackend.controller;

import com.microproject.cgibackend.DTO.FlightBookingDTO;
import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<Page<FlightDTO>> getFlightsByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String departureCity,
            @RequestParam(required = false) String arrivalCity,
            @RequestParam(required = false) LocalDate departureDate,
            @RequestParam(required = false) LocalDate arrivalDate,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long maxDuration,
            @RequestParam(required = false, defaultValue = "Tourist class") String selectedClass,
            @RequestParam(defaultValue = "null") String sortBy,
            @RequestParam(defaultValue = "null") String sortDirection
    ) {
        System.out.println("max duration " + maxDuration);
        return ResponseEntity.ok(
                flightService.getFlightsByParams(departureCity, arrivalCity,
                        departureDate, arrivalDate, minPrice, maxPrice, selectedClass, maxDuration,
                        sortBy, sortDirection, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightBookingDTO> getFlightForBooking(@PathVariable Long id) {

        return ResponseEntity.ok(flightService.getFlightBookingDTO(id));
    }

    @GetMapping("/departureCity")
    public ResponseEntity<List<String>> getDepartureCities() {

        return ResponseEntity.ok(flightService.getAllDepartureCities());
    }

    @GetMapping("/arrivalCity")
    public ResponseEntity<List<String>> getArrivalCities() {

        return ResponseEntity.ok(flightService.getAllArrivalCities());
    }

}
