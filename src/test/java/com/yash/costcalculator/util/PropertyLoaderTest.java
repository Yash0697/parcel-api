package com.yash.costcalculator.util;

import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.yash.costcalculator.util.PropertyLoaderImpl;

public class PropertyLoaderTest {

	@InjectMocks
	PropertyLoaderImpl propertyLoader;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void loadProperty() {
		String property = propertyLoader.loadProperty();
		assertNull(property);
	}
}
