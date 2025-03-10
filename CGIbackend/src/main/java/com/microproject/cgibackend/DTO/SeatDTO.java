package com.microproject.cgibackend.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = "ticketNumber")
public class SeatDTO {
    private int ticketNumber;
    private Long id;
    private Long flightId;
    private String seatNumber;
    private Boolean isAvailable;
    private String classType;
    private BigDecimal price;
    private Boolean moreLegSpace;
    private Boolean closeToExit;

    public SeatDTO(Long id, Long flightId, String seatNumber, Boolean isAvailable,
                   String classType, BigDecimal price, Boolean moreLegSpace, Boolean closeToExit) {
        this.id = id;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
        this.classType = classType;
        this.price = price;
        this.moreLegSpace = moreLegSpace;
        this.closeToExit = closeToExit;
    }
}
