package com.yash.costcalculator.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

@SuppressWarnings("deprecation")
public class AppConfigTest {

	
	
	@InjectMocks
	AppConfig appConfig;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetWebClientBuilder() {
		appConfig.getWebClientBuilder();
	}
}
