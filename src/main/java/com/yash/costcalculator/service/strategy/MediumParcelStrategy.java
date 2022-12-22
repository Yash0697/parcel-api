package com.yash.costcalculator.service.strategy;

public class MediumParcelStrategy implements CostStrategy {

	private double volume;

	MediumParcelStrategy(double volume) {
		this.volume = volume;
	}

	@Override
	public Double calculateCost() {
		return this.volume * 0.04;
	}

}
