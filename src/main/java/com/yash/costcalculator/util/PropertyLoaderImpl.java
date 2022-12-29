package com.yash.costcalculator.util;

import static com.yash.costcalculator.util.AppConstants.IS_COUPON_API_ACTIVE;

import org.springframework.stereotype.Component;

@Component
public class PropertyLoaderImpl {

	public String loadProperty() {
		return System.getProperty(IS_COUPON_API_ACTIVE);
	}

}
