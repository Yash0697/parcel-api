package com.yash.costcalculator.model;
import java.io.Serializable;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STRATEGY_PARAMS")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class StrategyDecisionParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="incrementGen", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "incrementGen")
	private int id;
	
	@Column(name = "WEIGHT")
	private Double weight;
	@Column(name = "VOLUME")
	private Double volume;
	@Column(name = "STRATEGY")
	private String strategy;
}
