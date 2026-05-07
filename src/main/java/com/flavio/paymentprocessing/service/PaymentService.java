package com.flavio.paymentprocessing.service;

import com.flavio.paymentprocessing.dto.*;
import com.flavio.paymentprocessing.entity.Payment;
import com.flavio.paymentprocessing.entity.PaymentAudit;
import com.flavio.paymentprocessing.entity.User;
import com.flavio.paymentprocessing.enums.PaymentStatus;
import com.flavio.paymentprocessing.exception.BusinessException;
import com.flavio.paymentprocessing.repository.PaymentAuditRepository;
import com.flavio.paymentprocessing.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentAuditRepository paymentAuditRepository;

    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO request, User user) {

        Payment payment = Payment.builder()
                .user(user)
                .amount(request.amount())
                .description(request.description())
                .status(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        saveAudit(
                savedPayment,
                null,
                PaymentStatus.PENDING,
                "Payment created"
        );

        return mapToResponse(savedPayment);
    }

    public PaymentResponseDTO getPaymentById(UUID paymentId, User user) {
        Payment payment = findPaymentById(paymentId);

        validateOwnership(payment, user);

        return mapToResponse(payment);
    }

    public List<PaymentResponseDTO> getPaymentsByUser(User user) {
        return paymentRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<PaymentHistoryResponseDTO> getPaymentHistory(UUID paymentId, User user) {
        Payment payment = findPaymentById(paymentId);

        validateOwnership(payment, user);

        return paymentAuditRepository.findByPayment(payment)
                .stream()
                .map(audit -> new PaymentHistoryResponseDTO(
                        audit.getOldStatus(),
                        audit.getNewStatus(),
                        audit.getReason(),
                        audit.getChangedAt()
                ))
                .toList();
    }

    public PaymentResponseDTO refundPayment(UUID paymentId, RefundRequestDTO request, User user) {
        Payment payment = findPaymentById(paymentId);

        validateOwnership(payment, user);

        if (payment.getStatus() != PaymentStatus.APPROVED) {
            throw new BusinessException("Only approved payments can be refunded");
        }

        PaymentStatus oldStatus = payment.getStatus();

        payment.setStatus(PaymentStatus.REFUNDED);

        Payment updatedPayment = paymentRepository.save(payment);

        saveAudit(
                updatedPayment,
                oldStatus,
                PaymentStatus.REFUNDED,
                request.reason()
        );

        return mapToResponse(updatedPayment);
    }

    private Payment findPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException("Payment not found"));
    }

    private void validateOwnership(Payment payment, User user) {
        if (!payment.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Access denied");
        }
    }

    private void saveAudit(
            Payment payment,
            PaymentStatus oldStatus,
            PaymentStatus newStatus,
            String reason
    ) {
        PaymentAudit audit = PaymentAudit.builder()
                .payment(payment)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .reason(reason)
                .correlationId(payment.getCorrelationId())
                .build();

        paymentAuditRepository.save(audit);
    }

    private PaymentResponseDTO mapToResponse(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getAmount(),
                payment.getDescription(),
                payment.getStatus(),
                payment.getCorrelationId(),
                payment.getCreatedAt()
        );
    }
}