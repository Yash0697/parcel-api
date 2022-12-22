package com.yash.costcalculator.model;


import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	
	private boolean error;
	private int statusCode;
	private Object payload;
	private String message;
	private Date responseDate;
	
	public ApiResponse(boolean error, int statusCode, Object payload, String message) {
		this.error = error;
		this.statusCode = statusCode;
		this.payload = payload;
		this.message = message;
	}
	
	public ApiResponse() {
		this.responseDate = new Date();
	}
	
}
