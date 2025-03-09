package com.microproject.cgibackend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SeatsRequestDTO {

    private List<SeatPreferencesDTO> preferences;
    private Boolean adjacentSeats;
}
