package com.microproject.cgibackend.DTO;

import lombok.Data;

@Data
public class SeatPreferencesDTO {

    private Long flightId;
    private String seatClass;
    private Boolean closeToWindow;
    private Boolean closeToExit;
    private Boolean extraLegSpace;
    private Integer numberOfSeats;
}
