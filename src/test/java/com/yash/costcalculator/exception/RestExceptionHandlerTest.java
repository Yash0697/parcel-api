package com.yash.costcalculator.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.costcalculator.ParcelApplication;
import com.yash.costcalculator.model.Parcel;

@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes={ ParcelApplication.class})
public class RestExceptionHandlerTest {
	
	@Autowired
    private WebApplicationContext webApplicationContext;

	@InjectMocks
	RestExceptionHandler restExceptionHandler;
	
	@Mock
	WebRequest webRequest;
	
	@Mock
	InsufficientValuesProvidedException insufficientValuesProvidedException;
	
	 private MockMvc mockMvc;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		 this.mockMvc = webAppContextSetup(webApplicationContext).build();
		
	}
	
	@Test
	public void testHandleInsufficientValuesProvidedException() throws Exception {
		String url = "/parcel/calculate-cost";
		Parcel parcel = new Parcel();
		ObjectMapper om = new ObjectMapper();
		String requestJson = om.writeValueAsString(parcel);
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson));
	}
 }
