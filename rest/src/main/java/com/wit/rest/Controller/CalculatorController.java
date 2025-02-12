package com.wit.rest.Controller;

import com.wit.calculator.Service.CalculatorService;
import com.wit.calculator.Service.KafkaService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;
    private final KafkaService kafkaService;

    public CalculatorController(CalculatorService calculatorService, KafkaService kafkaService) {
        this.calculatorService = calculatorService;
        this.kafkaService = kafkaService;
    }

    @GetMapping("/sum")
    public String sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        kafkaService.calculate("sum", a, b);
        return "Calculation sent to Kafka";
    }

    @GetMapping("/subtract")
    public String subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        kafkaService.calculate("subtract", a, b);
        return "Calculation sent to Kafka";
    }

    @GetMapping("/multiply")
    public String multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        kafkaService.calculate("multiply", a, b);
        return "Calculation sent to Kafka";
    }

    @GetMapping("/divide")
    public String divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        kafkaService.calculate("divide", a, b);
        return "Calculation sent to Kafka";
    }
}