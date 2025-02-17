package com.wit.calculator.Service;

import com.wit.calculator.Service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void result(BigDecimal result) {
        String message ="resultado: " + result;
        logger.info(message);
        kafkaTemplate.send("calculator-operations", message);
    }
}
