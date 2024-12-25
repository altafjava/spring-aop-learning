package com.altafjava.control.before;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
public class AbortExecutionAspect {

	@Before("execution(* com.altafjava.advice.Calculator.*(..))")
	public void controlExecution(JoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		if ("divide".equals(methodName)) {
			throw new RuntimeException("Simulated exception in Before Advice");
		}
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AbortExecutionAspect.class);
		Calculator calculator = context.getBean(Calculator.class);
		System.out.println("Add Result: " + calculator.add(10, 20));
		System.out.println("Subtract Result: " + calculator.subtract(30, 15));
		System.out.println("Multiply Result: " + calculator.multiply(5, 4));
		System.out.println("Divide Result: " + calculator.divide(10, 2));
		context.close();
	}

	@Bean
	public Calculator calculator() {
		return new Calculator();
	}
}
