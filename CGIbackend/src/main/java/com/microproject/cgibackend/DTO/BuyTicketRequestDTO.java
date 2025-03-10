package com.microproject.cgibackend.DTO;

import lombok.Data;

@Data
public class BuyTicketRequestDTO {

    private Long seatId;
    private String name;
    private String surname;
    private String email;
    private String phone;
}
