package com.flavio.paymentprocessing.repository;

import com.flavio.paymentprocessing.entity.Payment;
import com.flavio.paymentprocessing.entity.PaymentAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentAuditRepository extends JpaRepository<PaymentAudit, UUID> {
    List<PaymentAudit> findByPayment(Payment payment);
}