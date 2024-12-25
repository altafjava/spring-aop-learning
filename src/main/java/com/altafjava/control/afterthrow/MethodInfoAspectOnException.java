package com.altafjava.control.afterthrow;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.altafjava.advice.Calculator;

@Aspect
@Component
@Configuration
@EnableAspectJAutoProxy
public class MethodInfoAspectOnException {

	@AfterThrowing("execution(* com.altafjava.advice.Calculator.*(..))")
	public void accessMethodInfo(JoinPoint joinPoint) throws Throwable {
		// Static Information
		System.out.println("Method Name: " + joinPoint.getSignature().getName());
		System.out.println("Declaring Class: " + joinPoint.getSignature().getDeclaringTypeName());

		// Dynamic Information
		Object[] args = joinPoint.getArgs();
		System.out.println("Method Arguments: " + java.util.Arrays.toString(args));
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MethodInfoAspectOnException.class);
		Calculator calculator = context.getBean(Calculator.class);
		System.out.println("Add Result: " + calculator.add(10, 20));
		System.out.println("Subtract Result: " + calculator.subtract(30, 15));
		System.out.println("Multiply Result: " + calculator.multiply(5, 4));
		try {
			System.out.println("Divide Result: " + calculator.divide(10, 0));
		} catch (Exception ignored) {
		}
		context.close();
	}

	@Bean
	public Calculator calculator() {
		return new Calculator();
	}
}