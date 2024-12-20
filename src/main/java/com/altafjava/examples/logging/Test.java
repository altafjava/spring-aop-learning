package com.altafjava.examples.logging;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.altafjava.examples.logging")
public class Test {

	public static void main(String[] args) throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);
		UserService userService = context.getBean(UserService.class);
		
		userService.createUser("ethan_hunt", "tom.cruise@mfi.com");
		userService.updateUser(1L, "tony_stark", "tom.cruise@avengers.com");
		userService.deleteUser(1L);
		
		context.close();
	}
}