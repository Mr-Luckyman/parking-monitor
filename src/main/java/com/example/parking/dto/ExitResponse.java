package com.example.parking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExitResponse(
        String sessionId,
        LocalDateTime exitTime,
        long durationMinutes,
        BigDecimal amountDue
) {}
