package com.wit.calculator.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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
        if (message.contains("resultado")) {
            return;
        }
        String[] parts = message.split(",");
        String operation = parts[0];
        BigDecimal a = new BigDecimal(parts[1]);
        BigDecimal b = new BigDecimal(parts[2]);

        BigDecimal result = null;

        switch (operation) {
            case "sum":
                result = calculatorService.sum(a, b);
                break;
            case "subtract":
                result = calculatorService.subtract(a, b);
                break;
            case "multiply":
                result = calculatorService.multiply(a, b);
                break;
            case "divide":
                result = calculatorService.divide(a, b);
                break;
            default:
                logger.error("Unknown operation: " + operation);
        }

        if (result != null) {
            kafkaService.result(result);
        }
    }
}
