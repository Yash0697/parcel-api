package com.yash.costcalculator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parcel {
	private double weight;
	private double height;
	private double width;
	private double length;
	private String voucherCode;
}
