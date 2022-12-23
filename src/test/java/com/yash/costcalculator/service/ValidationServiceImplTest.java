package com.yash.costcalculator.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.service.impl.ValidationServiceImpl;

public class ValidationServiceImplTest {

	@Mock
	private LocalValidatorFactoryBean validator;
	
	@InjectMocks
	ValidationServiceImpl validationService;
	
	@Mock
	Parcel parcel;
	
	@Mock
	BindingResult result;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		parcel.setHeight(12.5);
		parcel.setWeight(38.1);
		parcel.setLength(10.0);
		parcel.setVoucherCode("GFI");
		parcel.setWidth(21.1);
	}
	
	@Test
	public void testValidateRequest() {
		assertNull(validationService.validateRequest(parcel));
	}
}
