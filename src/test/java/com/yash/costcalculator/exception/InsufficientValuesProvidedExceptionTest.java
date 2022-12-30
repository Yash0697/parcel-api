package com.yash.costcalculator.exception;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;

public class InsufficientValuesProvidedExceptionTest {

	@InjectMocks
	InsufficientValuesProvidedException insufficientValuesProvidedException;
	
	@Mock
	BindingResult bindingResult;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(insufficientValuesProvidedException, "result", bindingResult);
	}
	
	@Test
	public void testInsufficientValuesProvidedException() {
		BindingResult result = insufficientValuesProvidedException.getResult();
		insufficientValuesProvidedException.getLocalizedMessage();
		assertNotNull(result);
	}
}
