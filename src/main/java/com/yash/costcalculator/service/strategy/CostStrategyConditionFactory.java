package com.yash.costcalculator.service.strategy;

import java.util.LinkedHashMap;


public class CostStrategyConditionFactory {
	private LinkedHashMap<Double, CostStrategy> weightFactory = new LinkedHashMap<>();
	private LinkedHashMap<Double, CostStrategy> volumeFactory = new LinkedHashMap<>();
	
	public CostStrategyConditionFactory(double weight, double volume) {
		weightFactory.put(50.0, new RejectParcelStrategy());
		weightFactory.put(10.0, new HeavyParcelStrategy(weight));
		volumeFactory.put(1500.0, new SmallParcelStrategy(volume));
		volumeFactory.put(2500.0, new MediumParcelStrategy(volume));
	}
	
	
	public CostStrategy getStrategy(double weight, double volume) {
		for(double key: weightFactory.keySet()) {
			if(weight > key) return weightFactory.get(key);
		}
		for(double key: volumeFactory.keySet()) {
			if(volume < key) return volumeFactory.get(key);
		}
		return new LargeParcelStrategy(volume);
	}
}
