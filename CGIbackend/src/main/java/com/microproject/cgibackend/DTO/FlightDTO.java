package com.microproject.cgibackend.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private BigDecimal price;
    private Long availableSeatsNumber;
    private Long flightDuration;

//    public FlightDTO(Long id, String flightNumber,
//                     String departureCity, String arrivalCity,
//                     LocalDate departureDate, LocalTime departureTime,
//                     LocalDate arrivalDate, LocalTime arrivalTime,
//                     BigDecimal price, Long availableSeatsNumber) {
//        this.id = id;
//        this.flightNumber = flightNumber;
//        this.departureCity = departureCity;
//        this.arrivalCity = arrivalCity;
//        this.departureDate = departureDate;
//        this.departureTime = departureTime;
//        this.arrivalDate = arrivalDate;
//        this.arrivalTime = arrivalTime;
//        this.price = price;
//        this.availableSeatsNumber = availableSeatsNumber;
//    }

    public FlightDTO(Long id, String flightNumber,
                     String departureCity, String arrivalCity,
                     LocalDate departureDate, LocalTime departureTime,
                     LocalDate arrivalDate, LocalTime arrivalTime, BigDecimal price, Long flightDuration) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.flightDuration = flightDuration;
    }
}
