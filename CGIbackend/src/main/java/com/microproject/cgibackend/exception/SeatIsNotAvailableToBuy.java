package com.microproject.cgibackend.exception;

public class SeatIsNotAvailableToBuy extends RuntimeException{

    public SeatIsNotAvailableToBuy(String message) {
        super(message);
    }
}
