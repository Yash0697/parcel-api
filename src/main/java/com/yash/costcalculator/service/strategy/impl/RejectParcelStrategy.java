package com.yash.costcalculator.service.strategy.impl;

import com.yash.costcalculator.service.strategy.CostStrategy;

public class RejectParcelStrategy implements CostStrategy {

	@SuppressWarnings("unused")
	private double weight, volume;
	public RejectParcelStrategy(double weight, double volume) {
		this.weight = weight;
		this.volume = volume;
	}
	
	@Override
	public Double calculateCost() {
		return  0.0;
	}

}
