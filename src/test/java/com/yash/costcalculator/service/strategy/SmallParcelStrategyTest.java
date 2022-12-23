package com.yash.costcalculator.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class SmallParcelStrategyTest {

	SmallParcelStrategy smallParcelStrategy;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		smallParcelStrategy = new SmallParcelStrategy(1400);
	}

	@Test
	public void testCalculateCost() {
		assertEquals(42.0, smallParcelStrategy.calculateCost());
	}
}
