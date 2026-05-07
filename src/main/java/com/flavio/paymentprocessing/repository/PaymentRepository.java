package com.flavio.paymentprocessing.repository;

import com.flavio.paymentprocessing.entity.Payment;
import com.flavio.paymentprocessing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByUser(User user);
}