package com.yash.costcalculator.model;


import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	
	private boolean error;
	private int statusCode;
	private Map<String, Object> payload;
	private String message;
	private Date responseDate;
	
	public ApiResponse(boolean error, int statusCode, Map<String, Object> payload, String message) {
		this.error = error;
		this.statusCode = statusCode;
		this.payload = payload;
		this.message = message;
	}
	
	public ApiResponse(HttpStatus status, Map<String, Object> payload) {
		this.statusCode = status.value();
		this.payload = payload;
	}
	
	public ApiResponse() {
		this.responseDate = new Date();
	}
	
}
