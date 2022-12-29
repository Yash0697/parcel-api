package com.yash.costcalculator.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.yash.costcalculator.service.strategy.impl.RejectParcelStrategy;

public class RejectionStrategyTest {

	RejectParcelStrategy rejectParcelStrategy;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		rejectParcelStrategy = new RejectParcelStrategy(51, 1200);
	}

	@Test
	public void testCalculateCost() {
		assertEquals(0.0, rejectParcelStrategy.calculateCost());
	}
}
