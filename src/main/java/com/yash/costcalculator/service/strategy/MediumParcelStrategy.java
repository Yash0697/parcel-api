package com.yash.costcalculator.service.strategy;

public class MediumParcelStrategy implements CostStrategy {

	@SuppressWarnings("unused")
	private double volume, weight;

	MediumParcelStrategy(double weight, double volume) {
		this.volume = volume;
		this.weight = weight;
	}

	@Override
	public Double calculateCost() {
		return this.volume * 0.04;
	}

}
