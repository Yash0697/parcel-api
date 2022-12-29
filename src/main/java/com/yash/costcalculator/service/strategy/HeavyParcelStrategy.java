package com.yash.costcalculator.service.strategy;

public class HeavyParcelStrategy implements CostStrategy {

	@SuppressWarnings("unused")
	private double weight, volume;
	
	HeavyParcelStrategy(double weight, double volume) {
		this.weight = weight;
		this.volume = volume;
	}
	
	@Override
	public Double calculateCost() {
		return this.weight * 20;
	}

}
