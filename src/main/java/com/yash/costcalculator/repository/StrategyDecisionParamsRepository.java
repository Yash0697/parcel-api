package com.yash.costcalculator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yash.costcalculator.model.StrategyDecisionParams;

@Repository
@Component
public interface StrategyDecisionParamsRepository extends CrudRepository<StrategyDecisionParams, Integer> {

}
