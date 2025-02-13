package com.wit.calculator;

import com.wit.calculator.Service.CalculatorService;
import com.wit.calculator.Service.KafkaCalculatorService;
import com.wit.calculator.Service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "calculator-operations" })
public class KafkaCalculatorServiceTest {

    @Autowired
    private KafkaCalculatorService kafkaCalculatorService;

    @MockBean
    private KafkaService kafkaService;  // Mock KafkaService

    @MockBean
    private CalculatorService calculatorService;  // Mock CalculatorService

    @BeforeEach
    void setUp() {
        // Mock the CalculatorService methods
        Mockito.when(calculatorService.sum(new BigDecimal("3"), new BigDecimal("3"))).thenReturn(new BigDecimal("6"));
        Mockito.when(calculatorService.subtract(new BigDecimal("3"), new BigDecimal("3"))).thenReturn(new BigDecimal("0"));
        Mockito.when(calculatorService.multiply(new BigDecimal("3"), new BigDecimal("3"))).thenReturn(new BigDecimal("9"));
        Mockito.when(calculatorService.divide(new BigDecimal("3"), new BigDecimal("3"))).thenReturn(new BigDecimal("1"));
    }

    @Test
    void testConsumeMessageSum() {
        String message = "sum,3,3";
        kafkaCalculatorService.consume(message);
        verify(calculatorService).sum(new BigDecimal("3"), new BigDecimal("3"));
        verify(kafkaService).result(new BigDecimal("6"));
    }

    @Test
    void testConsumeMessageSubtract() {
        String message = "subtract,3,3";
        kafkaCalculatorService.consume(message);
        verify(calculatorService).subtract(new BigDecimal("3"), new BigDecimal("3"));
        verify(kafkaService).result(new BigDecimal("0"));
    }

    @Test
    void testConsumeMessageMultiply() {
        String message = "multiply,3,3";
        kafkaCalculatorService.consume(message);
        verify(calculatorService).multiply(new BigDecimal("3"), new BigDecimal("3"));
        verify(kafkaService).result(new BigDecimal("9"));
    }

    @Test
    void testConsumeMessageDivide() {
        String message = "divide,3,3";
        kafkaCalculatorService.consume(message);
        verify(calculatorService).divide(new BigDecimal("3"), new BigDecimal("3"));
        verify(kafkaService).result(new BigDecimal("1"));
    }
}
