package com.microproject.cgibackend.controller;

import com.microproject.cgibackend.DTO.BuyTicketRequestDTO;
import com.microproject.cgibackend.DTO.SeatDTO;
import com.microproject.cgibackend.DTO.TicketDTO;
import com.microproject.cgibackend.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<List<TicketDTO>> buyTicket(@RequestBody List<BuyTicketRequestDTO> ticketsToBuy) {

        return ResponseEntity.ok(ticketService.buyTickets(ticketsToBuy));
    }
}
