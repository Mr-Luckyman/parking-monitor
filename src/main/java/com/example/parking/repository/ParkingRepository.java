package com.example.parking.repository;

import com.example.parking.model.ParkingSession;
import com.example.parking.model.ParkingSpot;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository {

    ParkingSession save(ParkingSession session);

    Optional<ParkingSession> findById(String id);

    void update(ParkingSession session);

    List<ParkingSpot> findAllSpots();

    Optional<ParkingSpot> findFreeSpot(String zone);

    void occupySpot(int spotNumber, String vehicleId);

    void freeSpot(int spotNumber);
}
