package com.yash.costcalculator.service.strategy;

public class SmallParcelStrategy implements CostStrategy {

	@SuppressWarnings("unused")
	private double volume, weight;

	SmallParcelStrategy(double weight, double volume) {
		this.volume = volume;
		this.weight = weight;
	}

	@Override
	public Double calculateCost() {
		return this.volume * 0.03;
	}

}
