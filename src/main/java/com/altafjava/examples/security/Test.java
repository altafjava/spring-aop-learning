package com.altafjava.examples.security;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.altafjava.examples.security")
public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);
		TaskService taskService = context.getBean(TaskService.class);

		try {
			System.out.println("Simulating with ROLE_USER...");
//			SecurityContext.addRole("ROLE_USER");
//			SecurityContext.addRole("ROLE_ABC");
			SecurityContext.addRole("ROLE_ADMIN");
			taskService.performUserTask();

			System.out.println("\nSimulating with ROLE_ADMIN...");
			SecurityContext.clear();
			SecurityContext.addRole("ROLE_ADMIN");
//			SecurityContext.addRole("ROLE_USER");
			String result = taskService.performAdminTask();
			System.out.println("Result: " + result);
		} catch (SecurityException e) {
			System.err.println(e.getMessage());
		} finally {
			SecurityContext.clear();
		}

		context.close();
	}
}