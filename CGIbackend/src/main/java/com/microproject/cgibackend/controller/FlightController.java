package com.microproject.cgibackend.controller;

import com.microproject.cgibackend.DTO.FlightDTO;
import com.microproject.cgibackend.entity.Flight;
import com.microproject.cgibackend.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/all")
    public List<FlightDTO> getAllFlights() {
        return flightService.findAllFlights();
    }

}
