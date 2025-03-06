package com.microproject.cgibackend.service;

import com.microproject.cgibackend.DTO.AvailableSeatsDTO;
import com.microproject.cgibackend.DTO.ClassPricesDTO;
import com.microproject.cgibackend.DTO.FlightBookingDTO;
import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.repository.FlightRepository;
import com.microproject.cgibackend.repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
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
                                              BigDecimal minPrice, BigDecimal maxPrice, String seatClass, Long maxDuration,
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
        Page<FlightDTO> flights = flightRepository.findFlightsBySeatClass(departureCity, arrivalCity, departureDate, arrivalDate, minPrice, maxPrice, seatClass, maxDuration, pageable);
        for (FlightDTO flight : flights) {
            flight.setAvailableSeatsNumber(seatRepository.countAllAvailableByFlightId(flight.getId()));
        }
        return flights;
    }

    public FlightBookingDTO getFlightBookingDTO(Long id) {

        FlightBookingDTO flight = flightRepository
                .findFlightById(id)
                .orElseThrow(() -> new EntityNotFoundException("flight with id=" + id + " wasn't found."));

        ClassPricesDTO priceList = seatRepository.findClassPricesByFlightId(id);

        flight.setTouristClassPrice(priceList.getTouristClassPrice());
        flight.setBusinessClassPrice(priceList.getBusinessClassPrice());
        flight.setFirstClassPrice(priceList.getFirstClassPrice());

        AvailableSeatsDTO availableSeatsList = seatRepository
                .findAvailableSeatsNumberForEveryClassByFlightId(id);

        flight.setTouristClassAvailableSeats(availableSeatsList.getTouristClassAvailableSeats());
        flight.setBusinessClassAvailableSeats(availableSeatsList.getBusinessClassAvailableSeats());
        flight.setFirstClassAvailableSeats(availableSeatsList.getFirstClassAvailableSeats());

        return flight;
    }

    public List<String> getAllDepartureCities() {
        return flightRepository.findAllDepartureCities();
    }

    public List<String> getAllArrivalCities() {
        return flightRepository.findAllArrivalCities();
    }
}
