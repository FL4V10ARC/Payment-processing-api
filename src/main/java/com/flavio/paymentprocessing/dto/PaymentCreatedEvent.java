package com.flavio.paymentprocessing.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreatedEvent(

        UUID paymentId,

        UUID userId,

        BigDecimal amount,

        String status,

        String correlationId

) {
}