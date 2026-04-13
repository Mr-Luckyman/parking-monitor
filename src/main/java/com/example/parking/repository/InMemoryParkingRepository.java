package com.example.parking.repository;

import com.example.parking.model.ParkingSession;
import com.example.parking.model.ParkingSpot;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryParkingRepository implements ParkingRepository {

    private final Map<String, ParkingSession> sessions = new ConcurrentHashMap<>();
    private final Map<Integer, ParkingSpot> spots = new ConcurrentHashMap<>();

    public InMemoryParkingRepository() {
        char[] zones = {'A', 'B', 'C', 'D', 'E'};
        int spotNumber = 1;
        for (char zone : zones) {
            for (int i = 0; i < 10; i++) {
                spots.put(spotNumber, new ParkingSpot(spotNumber, String.valueOf(zone), false, null));
                spotNumber++;
            }
        }
    }

    @Override
    public ParkingSession save(ParkingSession session) {
        sessions.put(session.getId(), session);
        return session;
    }

    @Override
    public Optional<ParkingSession> findById(String id) {
        return Optional.ofNullable(sessions.get(id));
    }

    @Override
    public void update(ParkingSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public List<ParkingSpot> findAllSpots() {
        return List.copyOf(spots.values());
    }

    @Override
    public Optional<ParkingSpot> findFreeSpot(String zone) {
        return spots.values().stream()
                .filter(spot -> spot.zone().equals(zone) && !spot.isOccupied())
                .findFirst();
    }

    @Override
    public void occupySpot(int spotNumber, String vehicleId) {
        spots.computeIfPresent(spotNumber, (key, spot) ->
                new ParkingSpot(spot.spotNumber(), spot.zone(), true, vehicleId));
    }

    @Override
    public void freeSpot(int spotNumber) {
        spots.computeIfPresent(spotNumber, (key, spot) ->
                new ParkingSpot(spot.spotNumber(), spot.zone(), false, null));
    }
}
