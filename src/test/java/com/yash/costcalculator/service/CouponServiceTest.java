package com.yash.costcalculator.service;

import java.net.URI;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.yash.costcalculator.model.VoucherItem;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	CouponService couponService;
	
	@Mock
	ResponseEntity<VoucherItem> voucherItem;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(couponService, "apiUrl", "http://mockUrl.io");
		voucherItem.getBody().setDiscount(7.5f);
	}
	
	@Test
	public void testGetDiscount() {
		Mockito.when(restTemplate.getForEntity(Mockito.any(URI.class), Mockito.eq(VoucherItem.class)))
			.thenReturn(voucherItem);
		
		couponService.getDiscount("XYZ");
		
	}
	
	
 }
