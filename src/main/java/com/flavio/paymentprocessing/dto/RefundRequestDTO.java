package com.flavio.paymentprocessing.dto;

import jakarta.validation.constraints.NotBlank;

public record RefundRequestDTO(
        @NotBlank(message = "Refund reason is required")
        String reason
) {
}