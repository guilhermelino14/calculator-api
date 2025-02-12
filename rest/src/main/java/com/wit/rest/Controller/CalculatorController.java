package com.wit.rest.Controller;

import com.wit.calculator.Service.CalculatorService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/sum")
    public Map<String, BigDecimal> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return Map.of("result", calculatorService.sum(a, b));
    }

    @GetMapping("/subtract")
    public Map<String, BigDecimal> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return Map.of("result", calculatorService.subtract(a, b));
    }

    @GetMapping("/multiply")
    public Map<String, BigDecimal> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return Map.of("result", calculatorService.multiply(a, b));
    }

    @GetMapping("/divide")
    public Map<String, BigDecimal> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return Map.of("result", calculatorService.divide(a, b));
    }
}