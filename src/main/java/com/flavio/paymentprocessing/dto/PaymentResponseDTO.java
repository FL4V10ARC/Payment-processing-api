package com.flavio.paymentprocessing.dto;

import com.flavio.paymentprocessing.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        BigDecimal amount,
        String description,
        PaymentStatus status,
        String correlationId,
        LocalDateTime createdAt
) {
}