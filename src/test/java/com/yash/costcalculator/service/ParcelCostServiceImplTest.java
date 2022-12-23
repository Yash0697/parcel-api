package com.yash.costcalculator.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;
import com.yash.costcalculator.model.StrategyDecisionParams;
import com.yash.costcalculator.model.VoucherItem;
import com.yash.costcalculator.repository.StrategyDecisionParamsRepository;
import com.yash.costcalculator.service.impl.ParcelCostServiceImpl;
import com.yash.costcalculator.service.strategy.CostStrategyConditionFactory;

public class ParcelCostServiceImplTest {

	@InjectMocks
	private ParcelCostServiceImpl parcelCostService;

	@Mock
	private CouponService couponService;

	@Mock
	private StrategyDecisionParamsRepository strategyRepo;

	@Mock
	private Iterable<StrategyDecisionParams> strategies;

	private Parcel parcel;
	
	private ApiResponse apiRes;
	
	VoucherItem voucherResponse;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(parcelCostService, "conditionFactory", new CostStrategyConditionFactory());

		StrategyDecisionParams params1 = new StrategyDecisionParams(21, 23.0, 10000000.0, "HeavyParcelStrategy");
		StrategyDecisionParams params2 = new StrategyDecisionParams(22, 38.0, 10000000.0, "HeavyParcelStrategy");
		StrategyDecisionParams[] strategiesArray = { params1, params2 };
		strategies = () -> java.util.Arrays.stream(strategiesArray).iterator();

		parcel = new Parcel();
		parcel.setHeight(12.5);
		parcel.setWeight(38.1);
		parcel.setLength(10.0);
		parcel.setVoucherCode("GFI");
		parcel.setWidth(21.1);
		apiRes = new ApiResponse();
		apiRes.setStatusCode(HttpStatus.OK.value());
		Map<String, Object> payload = new HashMap<>();
		payload.put("parcel cost", 425.5);
		apiRes.setPayload(payload);
		voucherResponse = new VoucherItem();
		voucherResponse.setDiscount(7.5f);
	}

	@Test
	public void testCalculateCost() {
		when(strategyRepo.findAll()).thenReturn(strategies);
		when(couponService.getDiscount(Mockito.anyString()))
		.thenReturn(voucherResponse).thenReturn(null);
		ApiResponse calculateCost = parcelCostService.calculateCost(parcel);
		assertEquals(apiRes.getPayload().get("parcel cost"), calculateCost.getPayload().get("parcel cost"));
		calculateCost = parcelCostService.calculateCost(parcel);
		assertEquals(460.0, calculateCost.getPayload().get("parcel cost"));
	}
}
