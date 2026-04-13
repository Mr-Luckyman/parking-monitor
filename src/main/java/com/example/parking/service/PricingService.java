package com.example.parking.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PricingService {

    BigDecimal calculateCost(LocalDateTime entryTime, LocalDateTime exitTime);
}
