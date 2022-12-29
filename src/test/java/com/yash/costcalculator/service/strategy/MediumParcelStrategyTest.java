package com.yash.costcalculator.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class MediumParcelStrategyTest {

	MediumParcelStrategy mediumParcelStrategy;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mediumParcelStrategy = new MediumParcelStrategy(9, 2000);
	}

	@Test
	public void testCalculateCost() {
		assertEquals(80.0, mediumParcelStrategy.calculateCost());
	}
}
