package com.yash.costcalculator.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.model.StrategyDecisionParams;
import com.yash.costcalculator.model.VoucherItem;
import com.yash.costcalculator.repository.StrategyDecisionParamsRepository;
import com.yash.costcalculator.service.CouponService;
import com.yash.costcalculator.service.ParcelCostService;
import com.yash.costcalculator.service.strategy.CostStrategy;
import com.yash.costcalculator.service.strategy.CostStrategyConditionFactory;


@Service
public class ParcelCostServiceImpl implements ParcelCostService{

	
	private CostStrategyConditionFactory conditionFactory;
	
	@Autowired
	StrategyDecisionParamsRepository strategyRepo;
	
	@Autowired
	CouponService couponService;
	
	@Override
	public ApiResponse calculateCost(Parcel parcel) {
		VoucherItem voucherItem = null;
		Double calculatedCost = null;
		String voucherCode = null;
		Double discountedCost = null;
		
		Map<String, Object> payload = new HashMap<>();
		
		Double weight = parcel.getWeight();
		Double height = parcel.getHeight();
		Double width = parcel.getWidth();
		Double length = parcel.getLength();
		Double volume = height * width * length;
		
		Iterable<StrategyDecisionParams> strategiesIterable = strategyRepo.findAll();
		
		List<StrategyDecisionParams> strategies = new ArrayList<>();
		strategiesIterable.forEach(strategies::add);
		conditionFactory = new CostStrategyConditionFactory(strategies);
		
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
			payload.put("parcel cost", discounted);
			apiRes.setPayload(payload);
			return apiRes;
		}
		
		Double calculated = Double.valueOf(calculatedCost);
		apiRes.setStatusCode(HttpStatus.OK.value());
		payload.put("parcel cost", calculated);
		apiRes.setPayload(payload);
		return apiRes;
	}

}
