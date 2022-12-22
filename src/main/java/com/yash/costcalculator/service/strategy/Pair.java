package com.yash.costcalculator.service.strategy;

import lombok.Getter;

@Getter
public final class Pair {

	private final Double weight;
	private final Double volume;
	
	public Pair(Double weight, Double volume) {
		this.weight = weight;
		this.volume = volume;
	}
}
