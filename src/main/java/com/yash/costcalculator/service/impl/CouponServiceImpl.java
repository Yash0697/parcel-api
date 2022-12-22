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
import org.springframework.web.util.UriComponentsBuilder;

import com.yash.costcalculator.model.VoucherItem;
import com.yash.costcalculator.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);


	@Autowired
	WebClient.Builder webCLientBuilder;

	@Value("${API_URL}")
	private String apiUrl;

	@Override
	public VoucherItem getDiscount(String voucherCode) {
		Map<String, String> serviceVariables = new HashMap<>();
		serviceVariables.put("voucherCode", voucherCode);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
		URI uri = builder.buildAndExpand(serviceVariables).toUri();
		logger.debug("complete url of coupon service {}", uri);
		try {
			VoucherItem voucherItem = webCLientBuilder.build()
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(VoucherItem.class)
				.block();
			return voucherItem;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
