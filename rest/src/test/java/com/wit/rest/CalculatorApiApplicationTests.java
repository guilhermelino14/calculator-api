package com.wit.rest;

import com.wit.calculator.Service.KafkaService;
import com.wit.rest.Controller.CalculatorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


class CalculatorApiApplicationTests {

	private MockMvc mockMvc;
	private KafkaService kafkaService;
	private CalculatorController calculatorController;

	@BeforeEach
	void setUp() {
		kafkaService = Mockito.mock(KafkaService.class);
		calculatorController = new CalculatorController(kafkaService);
		mockMvc = MockMvcBuilders.standaloneSetup(calculatorController).build();
	}

	@Test
	void testSum() throws Exception {
		mockMvc.perform(get("/api/calculator/sum")
						.param("a", "2")
						.param("b", "3")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Calculation sent to Kafka"));

		Mockito.verify(kafkaService).calculate("sum", new BigDecimal("2"), new BigDecimal("3"));
	}

	@Test
	void testSubtract() throws Exception {
		mockMvc.perform(get("/api/calculator/subtract")
						.param("a", "5")
						.param("b", "3")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Calculation sent to Kafka"));

		Mockito.verify(kafkaService).calculate("subtract", new BigDecimal("5"), new BigDecimal("3"));
	}

	@Test
	void testMultiply() throws Exception {
		mockMvc.perform(get("/api/calculator/multiply")
						.param("a", "2")
						.param("b", "3")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Calculation sent to Kafka"));

		Mockito.verify(kafkaService).calculate("multiply", new BigDecimal("2"), new BigDecimal("3"));
	}

	@Test
	void testDivideByZero() throws Exception {
		mockMvc.perform(get("/api/calculator/divide")
						.param("a", "10")
						.param("b", "0")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Cannot divide by zero."));
	}

}
