package com.yash.costcalculator.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.yash.costcalculator.exception.InsufficientValuesProvidedException;
import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.model.VoucherItem;
import com.yash.costcalculator.service.CouponService;
import com.yash.costcalculator.service.ParcelCostService;
import com.yash.costcalculator.service.strategy.CostStrategy;
import com.yash.costcalculator.service.strategy.CostStrategyConditionFactory;
import com.yash.costcalculator.util.AppConstants;


@Service
public class ParcelCostServiceImpl implements ParcelCostService{

	
	private CostStrategyConditionFactory conditionFactory;
	
	@Autowired
	CouponService couponService;
	
	@Override
	public ApiResponse calculateCost(Parcel parcel) throws InsufficientValuesProvidedException {
		VoucherItem voucherItem = null;
		Double calculatedCost = null;
		String voucherCode = null;
		Double discountedCost = null;
		Date currDate = new Date();
		Double weight = parcel.getWeight();
		Double height = parcel.getHeight();
		Double width = parcel.getWeight();
		Double length = parcel.getLength();
		if(weight == null || height == null || width == null || length == null) 
			throw new InsufficientValuesProvidedException(AppConstants.BAD_REQUEST_MSG);
		Double volume = height * width * length;
		conditionFactory = new CostStrategyConditionFactory(weight, volume);
		
		CostStrategy costStrategy = conditionFactory.getStrategy(weight, volume);
		
		calculatedCost = costStrategy.calculateCost();
		
		voucherCode = parcel.getVoucherCode();
		
		voucherItem = couponService.getDiscount(voucherCode);
		
		ApiResponse apiRes = new ApiResponse();
		apiRes.setError(false);
		
		if(voucherItem != null && voucherItem.getDiscount() > 0 
//				&& voucherItem.getExpiry().after(currDate)
				) {
			discountedCost = calculatedCost - calculatedCost * (double) voucherItem.getDiscount() / 100;
			Double discounted = Double.valueOf(discountedCost);
			apiRes.setStatusCode(HttpStatus.OK.value());
			apiRes.setPayload(discounted);
			return apiRes;
		}
		
		Double calculated = Double.valueOf(calculatedCost);
		apiRes.setStatusCode(HttpStatus.OK.value());
		apiRes.setPayload(calculated);
		return apiRes;
	}

}
