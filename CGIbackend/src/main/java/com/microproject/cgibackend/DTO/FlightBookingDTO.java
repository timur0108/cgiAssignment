package com.microproject.cgibackend.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightBookingDTO {

    private Long id;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;

    private BigDecimal touristClassPrice;
    private BigDecimal firstClassPrice;
    private BigDecimal businessClassPrice;

    private Long touristClassAvailableSeats;
    private Long businessClassAvailableSeats;
    private Long firstClassAvailableSeats;

    private Long flightDuration;

    public FlightBookingDTO(Long id, String flightNumber, String departureCity,
                            String arrivalCity, LocalDate departureDate,
                            LocalTime departureTime, LocalDate arrivalDate,
                            LocalTime arrivalTime, Long flightDuration) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.flightDuration = flightDuration;
    }
}
