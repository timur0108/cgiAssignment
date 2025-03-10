package com.microproject.cgibackend.service;

import com.microproject.cgibackend.DTO.BuyTicketRequestDTO;
import com.microproject.cgibackend.DTO.SeatDTO;
import com.microproject.cgibackend.DTO.TicketDTO;
import com.microproject.cgibackend.entity.Flight;
import com.microproject.cgibackend.entity.Passenger;
import com.microproject.cgibackend.entity.Seat;
import com.microproject.cgibackend.entity.Ticket;
import com.microproject.cgibackend.exception.SeatIsNotAvailableToBuy;
import com.microproject.cgibackend.repository.PassengerRepository;
import com.microproject.cgibackend.repository.SeatRepository;
import com.microproject.cgibackend.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;

    @Transactional
    public List<TicketDTO> buyTickets(List<BuyTicketRequestDTO> ticketsToBuy) {

        List<TicketDTO> boughtTickets = new ArrayList<>();
        for (BuyTicketRequestDTO buyTicketRequestDTO : ticketsToBuy) {
            boughtTickets.add(buyOneTicket(buyTicketRequestDTO));
        }
        return boughtTickets;
    }

    protected TicketDTO buyOneTicket(BuyTicketRequestDTO buyTicketRequestDTO) {

        Long seatId = buyTicketRequestDTO.getSeatId();
        Seat seat = seatRepository
                .findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat with id=" + seatId + " wasnt found"));

        if (!seat.getIsAvailable()) throw new SeatIsNotAvailableToBuy("Seat with id=" + seatId + " is already booked");

        Passenger passenger = new Passenger();
        passenger.setFirstName(buyTicketRequestDTO.getName());
        passenger.setLastName(buyTicketRequestDTO.getSurname());
        passenger.setEmail(buyTicketRequestDTO.getEmail());
        passenger.setPhone(buyTicketRequestDTO.getPhone());

        passenger = passengerRepository.save(passenger);

        Ticket ticket = new Ticket();
        ticket.setSeat(seat);
        ticket.setPassenger(passenger);

        seat.setIsAvailable(false);
        seatRepository.save(seat);

        Flight flight = seat.getFlight();

        ticket = ticketRepository.save(ticket);

        return new TicketDTO(
                ticket.getId(),
                flight.getFlightNumber(), flight.getDepartureCity(), flight.getArrivalCity(),
                flight.getDepartureDate(), flight.getDepartureTime(), flight.getArrivalDate(),
                flight.getArrivalTime(), flight.getFlightDuration(), passenger.getFirstName(),
                passenger.getLastName(), passenger.getEmail(), passenger.getPhone(),
                seat.getSeatNumber(), seat.getPrice(), seat.getClassType()
        );
    }
}
