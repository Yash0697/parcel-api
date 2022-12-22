package com.yash.costcalculator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.costcalculator.exception.InsufficientValuesProvidedException;
import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.service.ParcelCostService;
import com.yash.costcalculator.util.ApiEndPoints;

@RestController
@RequestMapping(ApiEndPoints.PARCEL)
public class ParcelCostController {
	
	private static final Logger logger = LoggerFactory.getLogger(ParcelCostService.class);
	@Autowired
	ParcelCostService costService;
	
	@PostMapping(ApiEndPoints.CALCULATE_COST)
	public ApiResponse calculateParcelCost(@RequestBody Parcel parcel) throws InsufficientValuesProvidedException {
		logger.info("api {} is called", ApiEndPoints.CALCULATE_COST);
		ApiResponse apiResponse = costService.calculateCost(parcel);
		return apiResponse;
	}
}
