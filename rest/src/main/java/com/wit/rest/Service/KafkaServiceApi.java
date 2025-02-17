package com.wit.rest.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaServiceApi {

    private static final Logger logger = LoggerFactory.getLogger(KafkaServiceApi.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaServiceApi(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void calculate(String operation, BigDecimal a, BigDecimal b) {
        String message = operation + "," + a + "," + b;
        logger.info("Sending message: " + message);
        kafkaTemplate.send("calculator-operations", message);
    }
}
