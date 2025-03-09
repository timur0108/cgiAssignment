package com.microproject.cgibackend.controller;

import com.microproject.cgibackend.DTO.SeatDTO;
import com.microproject.cgibackend.DTO.SeatPreferencesDTO;
import com.microproject.cgibackend.DTO.SeatsRequestDTO;
import com.microproject.cgibackend.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/seat")
public class SeatController {

    private final SeatService service;
    private final SeatService seatService;

    @GetMapping("/{id}")
    public ResponseEntity<List<SeatDTO>> getALlSeatsForFlightId(@PathVariable Long id) {

        return ResponseEntity.ok(service.getAllSeatsForFlight(id));
    }

    @PostMapping()
    public ResponseEntity<Set<SeatDTO>> getRecommendedSeats(
            @RequestBody List<SeatPreferencesDTO> seatPreferencesDTO) {

        return ResponseEntity.ok(seatService.recommendSeats(seatPreferencesDTO));
    }
}
