package com.yash.costcalculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@OpenAPIDefinition(info = @Info(
//		title="Parcel Cost API",
//		version = "1.0",
//		description = "Api to calculate parcel delivery cost and apply coupon"
//		))
public class ParcelApplication {
	private static final Logger logger = LoggerFactory.getLogger(ParcelApplication.class);
	public static void main(String[] args) {
		logger.info("Parcel Application started");
		SpringApplication.run(ParcelApplication.class, args);
	}

}
