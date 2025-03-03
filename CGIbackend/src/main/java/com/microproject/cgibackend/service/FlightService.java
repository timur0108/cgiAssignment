package com.microproject.cgibackend.service;

import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.repository.FlightRepository;
import com.microproject.cgibackend.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FlightService {


    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    public Page<FlightDTO> getFlightsByParams(String departureCity, String arrivalCity,
                                              LocalDate departureDate, LocalDate arrivalDate,
                                              BigDecimal minPrice, BigDecimal maxPrice, String seatClass,
                                              String sortBy, String sortDirection, int page, int size) {

        Sort sort = Sort.unsorted();
        if (!sortBy.equals("null") && !sortDirection.equals("null")) {
            if (sortBy.equals("price")) {
                sort = Sort.by(Sort.Direction.fromString(sortDirection), "s.price");
            } else if (sortBy.equals("duration")) {
                sort = Sort.by(Sort.Direction.fromString(sortDirection), "flightDuration");
            }
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FlightDTO> flights = flightRepository.findFlightsBySeatClass(departureCity, arrivalCity, departureDate, arrivalDate, minPrice, maxPrice, seatClass, sortDirection, pageable);
        for (FlightDTO flight : flights) {
            flight.setAvailableSeatsNumber(seatRepository.countAllAvailableByFlightId(flight.getId()));
        }
        return flights;
    }

    public List<String> getAllDepartureCities() {
        return flightRepository.findAllDepartureCities();
    }

    public List<String> getAllArrivalCities() {
        return flightRepository.findAllArrivalCities();
    }
}
