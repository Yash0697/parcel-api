package com.yash.costcalculator.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.yash.costcalculator.model.VoucherItem;
import com.yash.costcalculator.service.impl.CouponServiceImpl;

import reactor.core.publisher.Mono;

public class CouponServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	CouponServiceImpl couponService;
	
	@Mock
	WebClient.Builder webClientBuilder;
	
	@Mock
	WebClient webClient;
	
	@SuppressWarnings("rawtypes")
	@Mock
	WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
	
	@SuppressWarnings("rawtypes")
	@Mock
	WebClient.RequestHeadersSpec requestHeadersSpec;
	
	@Mock
	WebClient.RequestBodyUriSpec requestBodyUriSpec;
	
	@Mock
	WebClient.RequestBodySpec requestBodySpec;

	@Mock
	WebClient.ResponseSpec responseSpec;
	
	@Mock
	Mono<VoucherItem> voucherItem;
	
	@Mock
	VoucherItem voucherResponse;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(couponService, "apiUrl", "http://mockUrl.io");
		voucherResponse.setDiscount(7.5f);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetDiscount() {
				
		when(webClientBuilder.build())
		.thenReturn(webClient);
				when(webClient.get())
		          .thenReturn(requestHeadersUriSpec);
		        when(requestHeadersUriSpec.uri(Mockito.any(URI.class)))
		          .thenReturn(requestHeadersSpec);
		        when(requestHeadersSpec.retrieve())
		          .thenReturn(responseSpec);
		        when(responseSpec.bodyToMono(VoucherItem.class))
		        .thenReturn(voucherItem);
		        when(voucherItem.block()).
		        thenReturn(voucherResponse).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST))
		        ;
				
		
		VoucherItem voucher = couponService.getDiscount("XYZ");
		assertEquals(voucherResponse.getDiscount(), voucher.getDiscount());		
		
		voucher = couponService.getDiscount("XYZ");
		assertNull(voucher);
		
		
	}
	
	
 }
