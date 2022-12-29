package com.yash.costcalculator.service;

import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;

public interface ParcelCostService  {

	ApiResponse calculateCost(Parcel parcel);
}
