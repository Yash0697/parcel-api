package com.yash.costcalculator.exception;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class InsufficientValuesProvidedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final BindingResult result;

	
	public InsufficientValuesProvidedException(BindingResult result) {
		this.result = result;
	}
}
