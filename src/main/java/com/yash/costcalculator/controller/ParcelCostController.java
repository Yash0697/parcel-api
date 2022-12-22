package com.yash.costcalculator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.costcalculator.exception.InsufficientValuesProvidedException;
import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.model.StrategyDecisionParams;
import com.yash.costcalculator.repository.StrategyDecisionParamsRepository;
import com.yash.costcalculator.service.ParcelCostService;
import com.yash.costcalculator.service.ValidationService;
import com.yash.costcalculator.util.ApiEndPoints;
import com.yash.costcalculator.util.AppConstants;

@RestController
@RequestMapping(ApiEndPoints.PARCEL)
public class ParcelCostController {
	
	private static final Logger logger = LoggerFactory.getLogger(ParcelCostService.class);
	@Autowired
	ParcelCostService costService;
	
	@Autowired
	ValidationService validationService;
	
	@Autowired
	StrategyDecisionParamsRepository repo;
	
	@PostMapping(ApiEndPoints.CALCULATE_COST)
	public ApiResponse calculateParcelCost(@RequestBody Parcel parcel) throws InsufficientValuesProvidedException {
		logger.info("api {} is called", ApiEndPoints.CALCULATE_COST);
		List<StrategyDecisionParams> params = (List<StrategyDecisionParams>)repo.findAll();
		System.out.println(params);
		ApiResponse response = validationService.validateRequest(parcel);

		if (response == null) {
			ApiResponse apiResponse = costService.calculateCost(parcel);
			return apiResponse;
		}
		
		ApiResponse noRes = new ApiResponse(true, HttpStatus.NO_CONTENT.value(), null, AppConstants.NO_CONTENT_MSG);
		return noRes;
	}
}
