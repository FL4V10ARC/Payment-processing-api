package com.flavio.paymentprocessing.repository;

import com.flavio.paymentprocessing.entity.OutboxEvent;
import com.flavio.paymentprocessing.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    List<OutboxEvent> findTop10ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}