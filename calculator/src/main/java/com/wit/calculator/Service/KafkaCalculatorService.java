package com.wit.calculator.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaCalculatorService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaCalculatorService.class);


    private final KafkaService kafkaService;
    private final CalculatorService calculatorService;

    public KafkaCalculatorService(KafkaService kafkaService, CalculatorService calculatorService) {
        this.kafkaService = kafkaService;
        this.calculatorService = calculatorService;
    }

    @KafkaListener(topics = "calculator-operations", groupId = "calculator_group")
    public void consume(String message) {
        String[] parts = message.split(",");
        if (parts.length < 4) {
            logger.error("Invalid message format: " + message);
            return;
        }

        String requestId = parts[0];
        String operation = parts[1];
        BigDecimal a = new BigDecimal(parts[2]);
        BigDecimal b = new BigDecimal(parts[3]);

        BigDecimal result = switch (operation) {
            case "sum" -> calculatorService.sum(a, b);
            case "subtract" -> calculatorService.subtract(a, b);
            case "multiply" -> calculatorService.multiply(a, b);
            case "divide" -> calculatorService.divide(a, b);
            default -> {
                logger.error("Unknown operation: " + operation);
                yield null;
            }
        };

        if (result != null) {
            String responseMessage = "Resultado:" + requestId + "," + result;
            logger.info("Calculation Result: " + responseMessage);
            kafkaService.getKafkaTemplate().send("calculator-results", responseMessage);
        }
    }
}
