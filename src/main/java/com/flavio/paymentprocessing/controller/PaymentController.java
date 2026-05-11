package com.flavio.paymentprocessing.controller;

import com.flavio.paymentprocessing.dto.*;
import com.flavio.paymentprocessing.entity.User;
import com.flavio.paymentprocessing.repository.UserRepository;
import com.flavio.paymentprocessing.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @PostMapping
    public PaymentResponseDTO createPayment(
            @RequestBody CreatePaymentRequestDTO request,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);
        return paymentService.createPayment(request, user);
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO getPayment(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);
        return paymentService.getPaymentById(id, user);
    }

    @GetMapping
    public List<PaymentResponseDTO> getPayments(
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);
        return paymentService.getPaymentsByUser(user);
    }

    @GetMapping("/{id}/history")
    public List<PaymentHistoryResponseDTO> getHistory(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);
        return paymentService.getPaymentHistory(id, user);
    }

    @PostMapping("/{id}/refund")
    public PaymentResponseDTO refundPayment(
            @PathVariable UUID id,
            @RequestBody RefundRequestDTO request,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);
        return paymentService.refundPayment(id, request, user);
    }

    private User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow();
    }
}