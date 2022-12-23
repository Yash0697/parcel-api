package com.yash.costcalculator.service.strategy;

import java.util.LinkedHashMap;
import java.util.List;


import com.yash.costcalculator.model.StrategyDecisionParams;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostStrategyConditionFactory {
	
	/*
	 * Factory class which loads all the configs from in memory h2 database,
	 * creates appropriate strategies. Here LinkedHashMap is used to store strategies
	 * because we need have the precedence in strategies.
	*/
	private LinkedHashMap<Pair, CostStrategy> factory = new LinkedHashMap<>();
	private List<StrategyDecisionParams> strategies;
	
	public CostStrategyConditionFactory(List<StrategyDecisionParams> strategies, double weight, double volume) {
		this.strategies = strategies;
		for(StrategyDecisionParams strategyDecisionParams: strategies) {
			String strategyName = strategyDecisionParams.getStrategy();
			double volumeFromStrategy = strategyDecisionParams.getVolume();
			double weightFromStrategy = strategyDecisionParams.getWeight();
			CostStrategy strategy = findStarategyFromName(strategyName, weight, volume);
			factory.put(new Pair(weightFromStrategy, volumeFromStrategy), strategy);
		}
		
	}
	
	
	public CostStrategyConditionFactory() {
	}

	/*
	 * Utility method which returns the appropriate factory based on volume and weight
	*/
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
	
	private CostStrategy findStarategyFromName(String strategyName, double weight, double volume) {
		switch(strategyName) {
			case "RejectParcelStrategy": 
				return new RejectParcelStrategy();
			case "HeavyParcelStrategy": 
				return new HeavyParcelStrategy(weight);
			case "SmallParcelStrategy":
				return new SmallParcelStrategy(volume);
			case "MediumParcelStrategy":
				return new MediumParcelStrategy(volume);
			default: return new LargeParcelStrategy(volume);
		}
	}
}
