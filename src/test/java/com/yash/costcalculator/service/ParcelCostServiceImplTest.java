package com.yash.costcalculator.service;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static com.yash.costcalculator.util.AppConstants.PARCEL_COST;
import static com.yash.costcalculator.util.AppConstants.COUPON_EXPIRED;
import static com.yash.costcalculator.util.AppConstants.INVALID_COUPON;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import com.yash.costcalculator.util.PropertyLoaderImpl;

public class ParcelCostServiceImplTest {

	@InjectMocks
	private ParcelCostServiceImpl parcelCostService;

	@Mock
	private CouponService couponService;

	@Mock
	private StrategyDecisionParamsRepository strategyRepo;

	@Mock
	private Iterable<StrategyDecisionParams> strategies;
	
	@Mock
	PropertyLoaderImpl propertyLoader; 

	private Parcel parcel;
	
	private ApiResponse apiRes;
	
	VoucherItem voucherResponse;

	@SuppressWarnings("deprecation")
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
		payload.put(PARCEL_COST, 704.85);
		apiRes.setPayload(payload);
		voucherResponse = new VoucherItem();
		voucherResponse.setDiscount(7.5f);
		voucherResponse.setExpiry(new Date(2023, 02, 02));
	}

	@Test
	public void testCalculateCost() {
		when(strategyRepo.findAll()).thenReturn(strategies);
		when(couponService.getDiscount(Mockito.anyString()))
		.thenReturn(voucherResponse).thenReturn(null);
		ApiResponse calculateCost = parcelCostService.calculateCost(parcel);
		assertEquals(apiRes.getPayload().get(PARCEL_COST), calculateCost.getPayload().get(PARCEL_COST));
		calculateCost = parcelCostService.calculateCost(parcel);
		//error in coupon service
		assertNull(calculateCost.getPayload());
	}
	
	@Test
	public void testCalculateCost_CouponExpired() {
		LocalDate localDate = LocalDate.of(2020, 01, 01);
		voucherResponse.setExpiry(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		when(strategyRepo.findAll()).thenReturn(strategies);
		when(couponService.getDiscount(Mockito.anyString()))
		.thenReturn(voucherResponse);
		when(propertyLoader.loadProperty()).thenReturn("true");
		ApiResponse calculatedCost = parcelCostService.calculateCost(parcel);
		assertTrue(COUPON_EXPIRED.equals(calculatedCost.getMessage()));
	}
	
	@Test
	public void testCalculateCost_InvalidCoupon() {
		voucherResponse.setDiscount(0.0f);
		when(strategyRepo.findAll()).thenReturn(strategies);
		when(couponService.getDiscount(Mockito.anyString()))
		.thenReturn(voucherResponse);
		ApiResponse calculatedCost = parcelCostService.calculateCost(parcel);
		assertTrue(INVALID_COUPON.equals(calculatedCost.getMessage()));
	}
}
