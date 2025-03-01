package com.microproject.cgibackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "class_type", nullable = false)
    private String classType;

    @OneToOne(mappedBy = "seat")
    private Ticket ticket;

}
