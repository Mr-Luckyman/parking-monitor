package com.example.parking.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class SimplePricingService implements PricingService {

    private static final BigDecimal HOURLY_RATE = new BigDecimal("100");

    @Override
    public BigDecimal calculateCost(LocalDateTime entryTime, LocalDateTime exitTime) {
        long hours = ChronoUnit.HOURS.between(entryTime, exitTime);
        if (ChronoUnit.MINUTES.between(entryTime, exitTime) % 60 > 0) {
            hours++;
        }
        return HOURLY_RATE.multiply(BigDecimal.valueOf(Math.max(hours, 1)));
    }
}
