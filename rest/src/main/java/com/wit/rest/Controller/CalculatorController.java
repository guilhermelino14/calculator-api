package com.wit.rest.Controller;

import com.wit.calculator.Service.CalculatorService;
import com.wit.calculator.Service.KafkaService;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private final KafkaService kafkaService;

    public CalculatorController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping("/sum")
    public ResponseEntity<?> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CompletableFuture<BigDecimal> future = kafkaService.calculate("sum", a, b);
        try {
            BigDecimal result = future.get(10, TimeUnit.SECONDS);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"result\": " + result + "}");
        } catch (TimeoutException e) {
            return ResponseEntity.status(504).contentType(MediaType.APPLICATION_JSON).body("{\"error\": \"Kafka response timeout\"}");
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body("{\"error\": \"Error processing request\"}");
        }
    }

    @GetMapping("/subtract")
    public ResponseEntity<String> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        kafkaService.calculate("subtract", a, b);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Calculation sent to Kafka");
    }

    @GetMapping("/multiply")
    public ResponseEntity<String> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        kafkaService.calculate("multiply", a, b);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Calculation sent to Kafka");
    }

    @GetMapping("/divide")
    public ResponseEntity<String> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            return ResponseEntity.badRequest().body("Cannot divide by zero.");
        }
        kafkaService.calculate("divide", a, b);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Calculation sent to Kafka");
    }
}