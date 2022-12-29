package com.yash.costcalculator.service.strategy.impl;

import com.yash.costcalculator.service.strategy.CostStrategy;

public class MediumParcelStrategy implements CostStrategy {

	@SuppressWarnings("unused")
	private double volume, weight;

	public MediumParcelStrategy(double weight, double volume) {
		this.volume = volume;
		this.weight = weight;
	}

	@Override
	public Double calculateCost() {
		return this.volume * 0.04;
	}

}
