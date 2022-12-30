package com.yash.costcalculator.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ApiEndPointsTest {
	
	@InjectMocks
	ApiEndPoints apiEndPoints;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testEndPoints() {
		String parcel = ApiEndPoints.PARCEL;
		String calculateCost = ApiEndPoints.CALCULATE_COST;
		
	}
 	
}
