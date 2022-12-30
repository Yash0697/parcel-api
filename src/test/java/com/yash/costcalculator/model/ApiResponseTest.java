package com.yash.costcalculator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.yash.costcalculator.model.ApiResponse;

public class ApiResponseTest {

	@InjectMocks
	ApiResponse apiResponse;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testApiResponse() {
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("parcel cost", 74.0);
		ApiResponse apiRes = new ApiResponse(HttpStatus.OK, payload);
		assertEquals(apiRes.getPayload(), payload);
	}
}
