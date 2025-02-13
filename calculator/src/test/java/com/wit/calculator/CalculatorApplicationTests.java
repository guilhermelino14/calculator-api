package com.wit.calculator;

import com.wit.calculator.Service.CalculatorService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorApplicationTests {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testSum() {
        BigDecimal result = calculatorService.sum(new BigDecimal("10"), new BigDecimal("10"));
        assertEquals(new BigDecimal("20"), result);
    }

    @Test
    void testSubtract() {
        BigDecimal result = calculatorService.subtract(new BigDecimal("10"), new BigDecimal("5"));
        assertEquals(new BigDecimal("5"), result);
    }

    @Test
    void testMultiply() {
        BigDecimal result = calculatorService.multiply(new BigDecimal("10"), new BigDecimal("10"));
        assertEquals(new BigDecimal("100"), result);
    }

    @Test
    void testDivide() {
        BigDecimal result = calculatorService.divide(new BigDecimal("10"), new BigDecimal("2"));
        assertEquals(new BigDecimal("5"), result);
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculatorService.divide(new BigDecimal("10"), new BigDecimal("0")));
    }
}
