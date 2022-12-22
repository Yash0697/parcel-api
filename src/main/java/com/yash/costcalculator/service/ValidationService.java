package com.yash.costcalculator.service;

import org.springframework.stereotype.Service;

import com.yash.costcalculator.model.ApiResponse;
import com.yash.costcalculator.model.Parcel;


@Service
public interface ValidationService {

	public ApiResponse validateRequest(Parcel parcel);
}
