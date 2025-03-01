package com.microproject.cgibackend.service;

import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FlightService {


    private final FlightRepository flightRepository;


    public List<FlightDTO> findAllFlights() {
        return flightRepository.findAllFlightsAsDTO();
    }
}
