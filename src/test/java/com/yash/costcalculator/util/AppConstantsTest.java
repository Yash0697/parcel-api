package com.yash.costcalculator.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class AppConstantsTest {

	@InjectMocks
	AppConstants appConstants;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAppConstants() {
		@SuppressWarnings("unused")
		AppConstants appconstants = new AppConstants();
	}
}
