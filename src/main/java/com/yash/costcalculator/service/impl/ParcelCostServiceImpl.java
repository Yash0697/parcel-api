package com.yash.costcalculator.service.impl;

import static com.yash.costcalculator.util.AppConstants.COUPON_API_FAILED;
import static com.yash.costcalculator.util.AppConstants.INVALID_COUPON;
import static com.yash.costcalculator.util.AppConstants.PARCEL_COST;
import static com.yash.costcalculator.util.AppConstants.COUPON_EXPIRED;

import java.util.ArrayList;
import java.util.Date;
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
public class ParcelCostServiceImpl implements ParcelCostService {

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
		Date currDate = new Date();
		Iterable<StrategyDecisionParams> strategiesIterable = strategyRepo.findAll();

		List<StrategyDecisionParams> strategies = new ArrayList<>();
		strategiesIterable.forEach(strategies::add);
		conditionFactory = new CostStrategyConditionFactory(strategies, weight, volume);

		CostStrategy costStrategy = conditionFactory.getStrategy(weight, volume);

		calculatedCost = costStrategy.calculateCost();

		voucherCode = parcel.getVoucherCode();

		voucherItem = couponService.getDiscount(voucherCode);

		ApiResponse apiRes = new ApiResponse();
		apiRes.setError(false);
		apiRes.setResponseDate(currDate);
		apiRes.setStatusCode(HttpStatus.OK.value());

		if (voucherItem == null) {
			apiRes.setError(true);
			apiRes.setStatusCode(HttpStatus.BAD_REQUEST.value());
			apiRes.setMessage(COUPON_API_FAILED);
			return apiRes;
		}

		if (voucherItem != null && voucherItem.getDiscount() == 0.0f) {
			apiRes.setMessage(INVALID_COUPON);
			return apiRes;
		}

		else if (voucherItem != null && voucherItem.getDiscount() > 0 && voucherItem.getExpiry().before(currDate)) {
			apiRes.setMessage(COUPON_EXPIRED);
			return apiRes;
		}

		discountedCost = calculatedCost - calculatedCost * (double) voucherItem.getDiscount() / 100;
		Double discounted = Double.valueOf(discountedCost);
		payload.put(PARCEL_COST, discounted);
		apiRes.setPayload(payload);
		return apiRes;
	}

}
