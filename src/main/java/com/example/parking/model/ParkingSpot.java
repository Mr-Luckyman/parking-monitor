package com.example.parking.model;

public record ParkingSpot(
        int spotNumber,
        String zone,
        boolean isOccupied,
        String vehicleId
) {}
