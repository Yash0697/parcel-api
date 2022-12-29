package com.yash.costcalculator.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class HeavyParcelStrategyTest {

	
	HeavyParcelStrategy heavyParcelStrategy;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		heavyParcelStrategy = new HeavyParcelStrategy(12, 12);
	}
	
	@Test
	public void testCalculateCost() {
		assertEquals(240.0, heavyParcelStrategy.calculateCost());
	}
}
