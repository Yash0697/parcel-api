package com.yash.costcalculator.service.strategy;

public class RejectParcelStrategy implements CostStrategy {

	RejectParcelStrategy() {
	}
	
	@Override
	public Double calculateCost() {
		return  0.0;
	}

}
