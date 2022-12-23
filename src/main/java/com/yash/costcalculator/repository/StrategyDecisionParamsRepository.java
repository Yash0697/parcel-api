package com.yash.costcalculator.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yash.costcalculator.model.StrategyDecisionParams;

@Repository
@Configurable
public interface StrategyDecisionParamsRepository extends CrudRepository<StrategyDecisionParams, Integer> {

}
