package com.yash.costcalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI api() {
		return new OpenAPI()
				.info( new Info().title("Parcel Cost API")
				.description("Api to calculate parcel delivery cost and apply coupon")
				.version("v0.0.1"));
	}
}