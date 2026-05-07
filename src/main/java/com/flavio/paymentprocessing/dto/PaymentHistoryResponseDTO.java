package com.flavio.paymentprocessing.dto;

import com.flavio.paymentprocessing.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentHistoryResponseDTO(
        PaymentStatus oldStatus,
        PaymentStatus newStatus,
        String reason,
        LocalDateTime changedAt
) {
}