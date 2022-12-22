package com.yash.costcalculator.service.strategy;

public class HeavyParcelStrategy implements CostStrategy {

	private double weight;
	
	HeavyParcelStrategy(double weight) {
		this.weight = weight;
	}
	
	@Override
	public Double calculateCost() {
		return this.weight * 20;
	}

}
