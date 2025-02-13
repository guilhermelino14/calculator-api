package com.wit.calculator.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class KafkaResultListener {

    private final KafkaService kafkaService;

    public KafkaResultListener(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @KafkaListener(topics = "calculator-results", groupId = "calculator_group")
    public void consumeResult(String message) {
        if (!message.startsWith("Resultado:")) {
            return;
        }

        String[] parts = message.split(",");
        if (parts.length < 2) {
            return;
        }

        String requestId = parts[0].replace("Resultado:", "").trim();
        BigDecimal result = new BigDecimal(parts[1]);

        kafkaService.completeCalculation(requestId, result);
    }
}
