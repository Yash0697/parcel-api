package com.yash.costcalculator.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class LargeParcelStrategyTest {

	LargeParcelStrategy largeParcelStrategy;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		largeParcelStrategy = new LargeParcelStrategy(2600);
	}

	@Test
	public void testCalculateCost() {
		assertEquals(130.0, largeParcelStrategy.calculateCost());
	}
}
