package com.yash.costcalculator.service.strategy;

public class LargeParcelStrategy implements CostStrategy {
	
	private double volume;

	LargeParcelStrategy(double volume) {
		this.volume = volume;
	}
	
	@Override
	public Double calculateCost() {
		return this.volume * 0.05;
	}

}
