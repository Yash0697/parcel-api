package com.yash.costcalculator.service.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yash.costcalculator.model.StrategyDecisionParams;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostStrategyConditionFactory {
	private static final Logger logger = LoggerFactory.getLogger(CostStrategyConditionFactory.class);

	/*
	 * Factory class which loads all the configs from in memory h2 database, creates
	 * appropriate strategies. Here LinkedHashMap is used to store strategies
	 * because we need have the precedence in strategies.
	 */
	private LinkedHashMap<Pair, CostStrategy> factory = new LinkedHashMap<>();
	private List<StrategyDecisionParams> strategies;


	private static final String PACKAGE = "com.yash.costcalculator.service.strategy.";

	public CostStrategyConditionFactory(List<StrategyDecisionParams> strategies, double weight, double volume) {
		this.strategies = strategies;
		for (StrategyDecisionParams strategyDecisionParams : strategies) {
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
	 * Utility method which returns the appropriate factory based on volume and
	 * weight
	 */
	public CostStrategy getStrategy(double weight, double volume) {
		for (Pair key : factory.keySet()) {
			if (weight > key.getWeight())
				return factory.get(key);
		}
		for (Pair key : factory.keySet()) {
			if (volume < key.getVolume())
				return factory.get(key);
		}
		return new LargeParcelStrategy(weight, volume);
	}

	private CostStrategy findStarategyFromName(String strategyName, double weight, double volume) {
		Constructor<CostStrategy> declaredConstructor;
		CostStrategy newInstance = null;
		try {
			@SuppressWarnings("unchecked")
			Class<CostStrategy> clazz = (Class<CostStrategy>) Class.forName(PACKAGE + strategyName);
			declaredConstructor = clazz.getDeclaredConstructor(double.class, double.class);
			newInstance = declaredConstructor.newInstance(weight, volume);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("NoSuchMethodException in findStrategyForName");
		} catch (InstantiationException e) {
			logger.error("InstantiationException in findStrategyForName");
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException in findStrategyForName");
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException in findStrategyForName");
		} catch (InvocationTargetException e) {
			logger.error("InvocationTargetException in findStrategyForName");
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in findStrategyForName");
		}
		return newInstance;
	}

//	private CostStrategy findStarategyFromName(String strategyName, double weight, double volume) {
//		switch(strategyName) {
//			case "RejectParcelStrategy": 
//				return new RejectParcelStrategy();
//			case "HeavyParcelStrategy": 
//				return new HeavyParcelStrategy(weight);
//			case "SmallParcelStrategy":
//				return new SmallParcelStrategy(volume);
//			case "MediumParcelStrategy":
//				return new MediumParcelStrategy(volume);
//			default: return new LargeParcelStrategy(volume);
//		}
//	}
}
