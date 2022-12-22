package com.yash.costcalculator.service.strategy;

import java.util.LinkedHashMap;


public class CostStrategyConditionFactory {
	
	private LinkedHashMap<Double, CostStrategy> weightFactory = new LinkedHashMap<>();
	private LinkedHashMap<Double, CostStrategy> volumeFactory = new LinkedHashMap<>();
	private LinkedHashMap<Pair, CostStrategy> factory = new LinkedHashMap<>();
	
	public CostStrategyConditionFactory(double weight, double volume) {
		factory.put(new Pair(50.0, Double.MIN_VALUE), new RejectParcelStrategy());
		factory.put(new Pair(10.0, Double.MIN_VALUE), new HeavyParcelStrategy(weight));
		factory.put(new Pair(Double.MAX_VALUE,1500.0), new SmallParcelStrategy(volume));
		factory.put(new Pair(Double.MAX_VALUE, 2500.0), new MediumParcelStrategy(volume));
	}
	
	
	public CostStrategy getStrategy(double weight, double volume) {
		for(Pair key: factory.keySet()) {
			if(weight > key.getWeight()) 
				return factory.get(key);
		}
		for(Pair key: factory.keySet()) {
			if(volume < key.getVolume()) 
				return factory.get(key);
		}
		return new LargeParcelStrategy(volume);
	}
}
