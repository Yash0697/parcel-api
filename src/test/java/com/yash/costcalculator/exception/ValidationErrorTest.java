package com.yash.costcalculator.exception;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class ValidationErrorTest {

	@InjectMocks
	ValidationError validationError;
		
	private Map<String, Set<String>> errors;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(validationError, "INSTANCE", validationError);
		errors = new HashMap<String, Set<String>>();
		@SuppressWarnings("unused")
		ValidationError validationErr = ValidationError.getInstance();
	}
	
	@Test
	public void testAddError() {
		ValidationError error = validationError.addError("PATH", "This field is mandatory");
		assertTrue(error.getMandatoryFields().size() == 0);
		errors.put("PATH", null);
		error = validationError.addError("PATH", "This field is mandatory");
		Set<String> errors2 = error.getErrors().keySet();
		assertTrue(errors2.size() == 1);
	}
	
	@Test
	public void testAddMandatoryField() {
		String field = "length";
		ValidationError error = validationError.addMandatoryField(field);
		assertTrue(error.getMandatoryFields().get(0).equals("length"));
	}
}
