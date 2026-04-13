package com.example.parking.dto;

import java.time.LocalDateTime;

public record EntryResponse(
        String sessionId,
        int spotNumber,
        LocalDateTime entryTime
) {}
