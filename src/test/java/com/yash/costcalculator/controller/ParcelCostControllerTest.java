package com.yash.costcalculator.controller;

import static com.yash.costcalculator.util.AppConstants.PARCEL_COST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.yash.costcalculator.exception.InsufficientValuesProvidedException;
import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.service.ParcelCostService;
import com.yash.costcalculator.service.ValidationService;

public class ParcelCostControllerTest {

	@Mock
	ParcelCostService costService;

	@Mock
	ValidationService validationService;

	@InjectMocks
	ParcelCostController parcelCostController;

	Parcel parcel;

	ApiResponse apiRes;
	
	@Mock
	BindingResult bindingResult;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		parcel = new Parcel();
		parcel.setHeight(12.5);
		parcel.setWeight(38.1);
		parcel.setLength(10.0);
		parcel.setVoucherCode("GFI");
		parcel.setWidth(21.1);

		apiRes = new ApiResponse();
		apiRes.setStatusCode(HttpStatus.OK.value());
		Map<String, Object> payload = new HashMap<>();
		payload.put(PARCEL_COST, 425.5);
		apiRes.setPayload(payload);
	}

	@Test
	public void getAllEmployeesAPI() throws Exception {
		when(validationService.validateRequest(any(Parcel.class))).thenReturn(null).thenReturn(apiRes);

		when(costService.calculateCost(any(Parcel.class))).thenReturn(apiRes);

		ApiResponse calculatedParcelCost = parcelCostController.calculateParcelCost(parcel);

		assertEquals(425.5, calculatedParcelCost.getPayload().get(PARCEL_COST));

		when(validationService.validateRequest(any(Parcel.class))).thenReturn(apiRes);

		calculatedParcelCost = parcelCostController.calculateParcelCost(parcel);
	}

}
