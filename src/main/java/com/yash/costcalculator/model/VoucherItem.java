package com.yash.costcalculator.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherItem {

	private String code;
	private float discount;
	private Date expiry;
}
