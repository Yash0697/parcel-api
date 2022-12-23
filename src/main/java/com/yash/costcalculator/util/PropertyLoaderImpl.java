package com.yash.costcalculator.util;

import static com.yash.costcalculator.util.AppConstants.IS_COUPON_API_ACTIVE;

public class PropertyLoaderImpl {

	public String loadProperty() {
		return System.getProperty(IS_COUPON_API_ACTIVE);
	}

}
