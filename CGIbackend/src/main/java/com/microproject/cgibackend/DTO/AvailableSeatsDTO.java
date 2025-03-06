package com.microproject.cgibackend.DTO;

import lombok.Data;

@Data
public class AvailableSeatsDTO {

    private Long touristClassAvailableSeats;
    private Long businessClassAvailableSeats;
    private Long firstClassAvailableSeats;

    public AvailableSeatsDTO(Long touristClassAvailableSeats, Long businessClassAvailableSeats, Long firstClassAvailableSeats) {
        this.touristClassAvailableSeats = touristClassAvailableSeats;
        this.businessClassAvailableSeats = businessClassAvailableSeats;
        this.firstClassAvailableSeats = firstClassAvailableSeats;
    }
}
