package com.altafjava.examples.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	public TransactionManager transactionManager() {
		return new TransactionManager();
	}
}
