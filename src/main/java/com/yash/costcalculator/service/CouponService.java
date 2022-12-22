package com.yash.costcalculator.service;

import org.springframework.stereotype.Service;

import com.yash.costcalculator.model.VoucherItem;

@Service
public interface CouponService {

	VoucherItem getDiscount(String voucherCode);
}
