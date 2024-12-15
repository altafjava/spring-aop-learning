package com.altafjava.examples.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.altafjava.examples.transaction")
public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);
		BankService bankService = context.getBean(BankService.class); // Proxy object(BankService$$SpringCGLIB$$0)

		try {
			System.out.println("Testing with amount 500:");
			bankService.transferMoney(500);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		try {
			System.out.println("\nTesting with amount 1500:");
			bankService.transferMoney(1500);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		context.close();
	}
}
