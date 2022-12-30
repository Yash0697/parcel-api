package com.yash.costcalculator.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class PairTest {
	
	@InjectMocks
	Pair pair;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		pair = new Pair(12.2, 122.0);
	}
	
	@Test
	public void testGetWeight() {
		double volume = pair.getVolume();
		double weight = pair.getWeight();
		assertEquals(12.2, weight);
		assertEquals(122.0, volume);
	}
 
}
