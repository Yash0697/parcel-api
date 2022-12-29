package com.yash.costcalculator.service.strategy;

public class RejectParcelStrategy implements CostStrategy {

	@SuppressWarnings("unused")
	private double weight, volume;
	RejectParcelStrategy(double weight, double volume) {
		this.weight = weight;
		this.volume = volume;
	}
	
	@Override
	public Double calculateCost() {
		return  0.0;
	}

}
