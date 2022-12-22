package com.yash.costcalculator.service;

import org.springframework.stereotype.Service;

import com.yash.costcalculator.exception.InsufficientValuesProvidedException;
import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;

@Service
public interface ParcelCostService  {

	ApiResponse calculateCost(Parcel parcel) throws InsufficientValuesProvidedException;
}
