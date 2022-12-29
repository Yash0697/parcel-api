package com.yash.costcalculator.service.impl;

import static com.yash.costcalculator.util.AppConstants.COUPON_NOT_APPLIED;
import static com.yash.costcalculator.util.AppConstants.COUPON_API_FAILED;
import static com.yash.costcalculator.util.AppConstants.INVALID_COUPON;
import static com.yash.costcalculator.util.AppConstants.PARCEL_COST;
import static com.yash.costcalculator.util.AppConstants.PARCEL_REJECTED;
import static com.yash.costcalculator.util.AppConstants.COUPON_EXPIRED;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yash.costcalculator.util.PropertyLoaderImpl;

@Service
public class ParcelCostServiceImpl implements ParcelCostService {
	
	private static final Logger logger = LoggerFactory.getLogger(ParcelCostServiceImpl.class);
	

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

		Map<String, Object> payload = new HashMap<>();

		Double weight = parcel.getWeight();
		Double height = parcel.getHeight();
		Double width = parcel.getWidth();
		Double length = parcel.getLength();
		Double volume = height * width * length;
		Date currDate = new Date();
		Iterable<StrategyDecisionParams> strategiesIterable = strategyRepo.findAll();
		ApiResponse apiRes = new ApiResponse();
		PropertyLoaderImpl propertyLoader = new PropertyLoaderImpl();
		String couponApiActive = propertyLoader.loadProperty();

		conditionFactory = loadCostStrategies(weight, volume, strategiesIterable);

		CostStrategy costStrategy = conditionFactory.getStrategy(weight, volume);

		logger.info("cost strategy retrieved for given parcel {}", costStrategy.getClass().getName());
		
		calculatedCost = costStrategy.calculateCost();

		if (calculatedCost == 0.0)
			apiRes.setMessage(PARCEL_REJECTED);

		voucherCode = checkVoucherCode(parcel);
		
		apiRes.setError(false);
		apiRes.setResponseDate(currDate);
		apiRes.setStatusCode(HttpStatus.OK.value());
		if(voucherCode == null) {
			logger.info(COUPON_NOT_APPLIED);
			payload.put(PARCEL_COST, calculatedCost);
			apiRes.setMessage(COUPON_NOT_APPLIED);
			apiRes.setPayload(payload);
			return apiRes;
		}

		voucherItem = couponService.getDiscount(voucherCode);


		if (voucherItem == null) {
			logger.error(COUPON_API_FAILED);
			apiRes.setError(true);
			apiRes.setStatusCode(HttpStatus.BAD_REQUEST.value());
			apiRes.setMessage(COUPON_API_FAILED);
			return apiRes;
		}

		if (voucherItem != null && voucherItem.getDiscount() == 0.0f) {
			apiRes.setMessage(INVALID_COUPON);
			return apiRes;
		}

		else if (voucherItem != null && voucherItem.getDiscount() > 0 
				&& voucherItem.getExpiry().before(currDate)
				&& couponApiActive != null
				&& couponApiActive.equalsIgnoreCase("true")
				) {
			apiRes.setMessage(COUPON_EXPIRED);
			return apiRes;
		}

		Double discounted = calculateDiscountedPrice(calculatedCost, voucherItem);
		payload.put(PARCEL_COST, discounted);
		apiRes.setPayload(payload);
		return apiRes;
	}

	private String checkVoucherCode(Parcel parcel) {
		String voucherCode;
		voucherCode = parcel.getVoucherCode();
		return voucherCode;
	}

	private CostStrategyConditionFactory loadCostStrategies(Double weight, Double volume, Iterable<StrategyDecisionParams> strategiesIterable) {
		List<StrategyDecisionParams> strategies = new ArrayList<>();
		strategiesIterable.forEach(strategies::add);
		return new CostStrategyConditionFactory(strategies, weight, volume);
	}
	
	private Double calculateDiscountedPrice(double calculatedCost, VoucherItem voucherItem) {
		return calculatedCost - calculatedCost * (double) voucherItem.getDiscount() / 100;
	}

}
