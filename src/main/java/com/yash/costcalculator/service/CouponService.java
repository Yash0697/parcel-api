package com.yash.costcalculator.service;

import com.yash.costcalculator.model.VoucherItem;

public interface CouponService {

	VoucherItem getDiscount(String voucherCode);
}
