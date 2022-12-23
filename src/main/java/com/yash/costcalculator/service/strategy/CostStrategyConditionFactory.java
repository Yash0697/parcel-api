package com.yash.costcalculator.service.strategy;

import java.util.LinkedHashMap;
import java.util.List;


import com.yash.costcalculator.model.StrategyDecisionParams;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostStrategyConditionFactory {
	
	
	
	private LinkedHashMap<Pair, CostStrategy> factory = new LinkedHashMap<>();
	private List<StrategyDecisionParams> strategies;
	
	public CostStrategyConditionFactory(List<StrategyDecisionParams> strategies) {
		this.strategies = strategies;
		for(StrategyDecisionParams strategyDecisionParams: strategies) {
			String strategyName = strategyDecisionParams.getStrategy();
			double volumeFromStrategy = strategyDecisionParams.getVolume();
			double weightFromStrategy = strategyDecisionParams.getWeight();
			CostStrategy strategy = findStarategyFromName(strategyName, weightFromStrategy, volumeFromStrategy);
			factory.put(new Pair(weightFromStrategy, volumeFromStrategy), strategy);
			
		}
		
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
