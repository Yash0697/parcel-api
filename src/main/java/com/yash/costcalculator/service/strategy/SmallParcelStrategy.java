package com.yash.costcalculator.service.strategy;

public class SmallParcelStrategy implements CostStrategy {

	private double volume;

	SmallParcelStrategy(double volume) {
		this.volume = volume;
	}

	@Override
	public Double calculateCost() {
		return this.volume * 0.05;
	}

}
