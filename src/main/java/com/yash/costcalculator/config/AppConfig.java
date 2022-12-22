package com.yash.costcalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Configuration
public class AppConfig {

	
	@Bean
    public Builder getWebClientBuilder() {
    	WebClient.Builder builder = WebClient.builder();
    	return builder;
    }
	
}
