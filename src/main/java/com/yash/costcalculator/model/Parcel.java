package com.yash.costcalculator.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parcel {
	@NotNull(message = "NotNull: you must provide weight in the request")
	private Double weight;
	@NotNull(message = "NotNull: you must provide height in the request")
	private Double height;
	@NotNull(message = "NotNull: you must provide width in the request")
	private Double width;
	@NotNull(message = "NotNull: you must provide length in the request")
	private Double length;
	private String voucherCode;
}
