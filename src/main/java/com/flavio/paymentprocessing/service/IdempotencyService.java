package com.flavio.paymentprocessing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final StringRedisTemplate redisTemplate;

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    public String getPaymentId(String idempotencyKey) {
        return redisTemplate.opsForValue().get(idempotencyKey);
    }

    public void savePaymentId(String idempotencyKey, String paymentId) {
        redisTemplate.opsForValue().set(
                idempotencyKey,
                paymentId,
                EXPIRATION_TIME
        );
    }
}