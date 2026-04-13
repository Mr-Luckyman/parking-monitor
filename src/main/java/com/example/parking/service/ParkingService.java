package com.example.parking.service;

import com.example.parking.dto.EntryRequest;
import com.example.parking.dto.EntryResponse;
import com.example.parking.dto.ExitResponse;
import com.example.parking.exception.NoSpotsAvailableException;
import com.example.parking.exception.SessionNotFoundException;
import com.example.parking.model.ParkingSession;
import com.example.parking.model.ParkingSpot;
import com.example.parking.model.PaymentStatus;
import com.example.parking.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository repository;
    private final PricingService pricingService;

    public EntryResponse registerEntry(EntryRequest request) {
        log.info("Vehicle {} entering zone {}", request.vehicleId(), request.zone());

        ParkingSpot freeSpot = repository.findFreeSpot(request.zone())
                .orElseThrow(() -> {
                    log.error("No spots available in zone {}", request.zone());
                    return new NoSpotsAvailableException(request.zone());
                });

        ParkingSession session = ParkingSession.builder()
                .id(UUID.randomUUID().toString())
                .vehicleId(request.vehicleId())
                .zone(request.zone())
                .spotNumber(freeSpot.spotNumber())
                .entryTime(request.timestamp())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        repository.save(session);
        repository.occupySpot(freeSpot.spotNumber(), request.vehicleId());

        return new EntryResponse(session.getId(), session.getSpotNumber(), session.getEntryTime());
    }

    public ExitResponse registerExit(String sessionId) {
        log.info("Vehicle {} exiting, session {}", sessionId, sessionId);

        ParkingSession session = repository.findById(sessionId)
                .orElseThrow(() -> {
                    log.error("Session {} not found", sessionId);
                    return new SessionNotFoundException(sessionId);
                });

        LocalDateTime exitTime = LocalDateTime.now();
        session.setExitTime(exitTime);

        long durationMinutes = Duration.between(session.getEntryTime(), exitTime).toMinutes();
        BigDecimal amountDue = pricingService.calculateCost(session.getEntryTime(), exitTime);

        session.setPaymentStatus(PaymentStatus.PAID);
        repository.update(session);
        repository.freeSpot(session.getSpotNumber());

        return new ExitResponse(session.getId(), exitTime, durationMinutes, amountDue);
    }
}
