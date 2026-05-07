package com.flavio.paymentprocessing.entity;

import com.flavio.paymentprocessing.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_audits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", length = 30)
    private PaymentStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 30)
    private PaymentStatus newStatus;

    @Column(nullable = false, length = 255)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    @Column(nullable = false, length = 100)
    private String correlationId;

    @PrePersist
    public void prePersist() {
        this.changedAt = LocalDateTime.now();
    }
}