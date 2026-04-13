package com.example.parking.exception;

public class NoSpotsAvailableException extends RuntimeException {

    public NoSpotsAvailableException(String zone) {
        super("Нет свободных мест в зоне " + zone);
    }
}
