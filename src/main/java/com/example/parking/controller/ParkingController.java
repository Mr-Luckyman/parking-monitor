package com.example.parking.controller;

import com.example.parking.dto.EntryRequest;
import com.example.parking.dto.EntryResponse;
import com.example.parking.dto.ExitResponse;
import com.example.parking.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/enter")
    public ResponseEntity<EntryResponse> enter(@RequestBody @Valid EntryRequest request) {
        EntryResponse response = parkingService.registerEntry(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/exit/{sessionId}")
    public ResponseEntity<ExitResponse> exit(@PathVariable String sessionId) {
        ExitResponse response = parkingService.registerExit(sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/spots/{zone}")
    public ResponseEntity<List<Integer>> getFreeSpots(@PathVariable String zone) {
        List<Integer> freeSpots = parkingService.getFreeSpotsByZone(zone);
        return ResponseEntity.ok(freeSpots);
    }
}
