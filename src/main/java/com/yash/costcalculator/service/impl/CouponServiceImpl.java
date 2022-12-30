package com.yash.costcalculator.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.yash.costcalculator.model.VoucherItem;
import com.yash.costcalculator.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	private static final String VOUCHER_CODE = "voucherCode";

	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

	@Autowired
	WebClient.Builder webCLientBuilder;

	@Value("${API_URL}")
	private String apiUrl;

	@Override
	public VoucherItem getDiscount(String voucherCode) {
		Map<String, String> serviceVariables = new HashMap<>();
		serviceVariables.put(VOUCHER_CODE, voucherCode);
		VoucherItem voucherItem = null;
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
		URI uri = builder.buildAndExpand(serviceVariables).toUri();
		logger.debug("complete url of coupon service {}", uri);
		try {
			voucherItem = webCLientBuilder.build().get().uri(uri).retrieve().bodyToMono(VoucherItem.class).block();
			return voucherItem;
		} catch (HttpClientErrorException | HttpServerErrorException | WebClientRequestException e) {
			logger.error("HttpClientErrorException occurred while calling coupon api");
			return null;
		} catch (WebClientResponseException e) {
			logger.error("WebClientResponseException occurred while calling coupon api. Will return 0.0f as discount percentage");
			voucherItem = new VoucherItem();
			voucherItem.setDiscount(0.0f);
			return voucherItem;
		}
	}

}
