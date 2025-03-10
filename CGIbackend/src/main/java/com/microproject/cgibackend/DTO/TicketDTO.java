package com.microproject.cgibackend.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TicketDTO {
    private Long id;

    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private Long flightDuration;

    private String passengerName;
    private String passengerSurname;
    private String passengerEmail;
    private String passengerPhone;

    private String seatNumber;
    private BigDecimal price;
    private String classType;

    public TicketDTO(Long id, String flightNumber, String departureCity, String arrivalCity, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, Long flightDuration, String passengerName, String passengerSurname, String passengerEmail, String passengerPhone, String seatNumber, BigDecimal price, String classType) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.flightDuration = flightDuration;
        this.passengerName = passengerName;
        this.passengerSurname = passengerSurname;
        this.passengerEmail = passengerEmail;
        this.passengerPhone = passengerPhone;
        this.seatNumber = seatNumber;
        this.price = price;
        this.classType = classType;
    }
}
