package com.yash.costcalculator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class StrategyDecisionParamsTest {

	@InjectMocks
	StrategyDecisionParams strategyDecisionParams;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testStrategyDecisionParams() {
		StrategyDecisionParams decisionParams = new StrategyDecisionParams();
		decisionParams.setId(23);
		decisionParams.setStrategy("RejectParcelStrategy");
		decisionParams.setVolume(121.1);
		decisionParams.setWeight(16.0);
		StrategyDecisionParams decisionParams1 = new StrategyDecisionParams(23,16.0, 121.1, "RejectParcelStrategy");
		assertEquals(decisionParams.getId(), decisionParams1.getId());
		assertEquals(decisionParams.getStrategy(), decisionParams1.getStrategy());
		assertEquals(decisionParams.getVolume(), decisionParams1.getVolume());
		assertEquals(decisionParams.getWeight(), decisionParams1.getWeight());
	
	}
	
}
