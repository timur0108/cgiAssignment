package com.microproject.cgibackend.service;

import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FlightService {


    private final FlightRepository flightRepository;


    public Page<FlightDTO> findAllFlights(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return flightRepository.findAllFlightsAsDTO(pageable);
    }
}
