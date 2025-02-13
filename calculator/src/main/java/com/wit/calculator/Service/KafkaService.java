package com.wit.calculator.Service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ConcurrentMap<String, CompletableFuture<BigDecimal>> pendingRequests = new ConcurrentHashMap<>();

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<BigDecimal> calculate(String operation, BigDecimal a, BigDecimal b) {
        String requestId = operation + "-" + a + "-" + b; // Unique key
        CompletableFuture<BigDecimal> future = new CompletableFuture<>();

        pendingRequests.put(requestId, future);

        String message = requestId + "," + operation + "," + a + "," + b;
        kafkaTemplate.send("calculator-operations", message);

        return future;
    }

    public void completeCalculation(String requestId, BigDecimal result) {
        CompletableFuture<BigDecimal> future = pendingRequests.remove(requestId);
        if (future != null) {
            future.complete(result);
        }
    }

    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }
}
