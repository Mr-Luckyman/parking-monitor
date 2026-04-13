package com.example.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record EntryRequest(
        @NotBlank String vehicleId,
        @NotBlank @Pattern(regexp = "[A-Z]") String zone,
        @NotNull LocalDateTime timestamp
) {}
