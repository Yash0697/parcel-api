package com.yash.costcalculator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.yash.costcalculator.exception.InsufficientValuesProvidedException;
import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {
	
	@Autowired
	private LocalValidatorFactoryBean validator;
	


	@Override
	public ApiResponse validateRequest(Parcel parcel) {
		BindingResult result = new BindException(parcel, Parcel.class.getSimpleName());
		validator.validate(parcel, result);
		if(result.hasErrors()) {
			throw new InsufficientValuesProvidedException(result);
		}
		return null;
	}

}
