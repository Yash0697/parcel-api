package com.yash.costcalculator.service.strategy;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yash.costcalculator.model.StrategyDecisionParams;
import com.yash.costcalculator.repository.StrategyDecisionParamsRepository;

@Component
public class CostStrategyConditionFactory {
	
	@Autowired
	StrategyDecisionParamsRepository strategyRepo;
	
	
//	private LinkedHashMap<Double, CostStrategy> weightFactory = new LinkedHashMap<>();
//	private LinkedHashMap<Double, CostStrategy> volumeFactory = new LinkedHashMap<>();
	private LinkedHashMap<Pair, CostStrategy> factory = new LinkedHashMap<>();
	
	public CostStrategyConditionFactory() {}
	public CostStrategyConditionFactory(double weight, double volume) {
		List<StrategyDecisionParams> strategies = (List<StrategyDecisionParams>) strategyRepo.findAll();
		for(StrategyDecisionParams strategyDecisionParams: strategies) {
			String strategyName = strategyDecisionParams.getStrategy();
			double volumeFromStrategy = strategyDecisionParams.getVolume();
			double weightFromStrategy = strategyDecisionParams.getWeight();
			CostStrategy strategy = findStarategyFromName(strategyName, weightFromStrategy, volumeFromStrategy);
			factory.put(new Pair(weightFromStrategy, volumeFromStrategy), strategy);
			
		}
		
//		factory.put(new Pair(10.0, Double.MIN_VALUE), new HeavyParcelStrategy(weight));
//		factory.put(new Pair(Double.MAX_VALUE,1500.0), new SmallParcelStrategy(volume));
//		factory.put(new Pair(Double.MAX_VALUE, 2500.0), new MediumParcelStrategy(volume));
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
