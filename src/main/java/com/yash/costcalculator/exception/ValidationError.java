package com.yash.costcalculator.exception;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class ValidationError {
 
	List<String> mandatoryFields = new ArrayList<>();
	Map<String, Set<String>> errors = new HashMap<>();
	
	private static ValidationError INSTANCE = null;
	  	
	public static ValidationError getInstance() {
		if(INSTANCE == null) return new ValidationError();
		return INSTANCE;
	}
	
	public ValidationError addError(String path, String message) {
		Set<String> errorMessages = null;
		if(this.errors.containsKey(path)) {
			errorMessages = errors.get(path);
		}
		else {
			errorMessages = new HashSet<>();
			this.errors.put(path, errorMessages);
		}
		errorMessages.add(message);
		return this;
			
	}
	
	public ValidationError addMandatoryField(String field) {
		mandatoryFields.add(field);
		return this;
	}
}
