package com.altafjava.examples.exception;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.altafjava.examples.exception")
public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);

		// Get the proxy object for BankService
		BankService bankService = context.getBean(BankService.class);

		System.out.println("Testing with valid transfer:");
		bankService.transferMoney(500); // Intercepted by proxy

		System.out.println("\nTesting with invalid withdrawal (negative amount):");
		try {
			bankService.withdrawMoney(-500); // Intercepted by proxy
		} catch (Exception ignored) {
		}

		System.out.println("\nTesting with transfer exceeding limit:");
		try {
			bankService.transferMoney(1500); // Intercepted by proxy
		} catch (Exception ignored) {
		}

		context.close();
	}
}
