package com.altafjava.advice.around.control;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
public class ControlExecutionAspect {

	@Around("execution(* com.altafjava.advice.Calculator.*(..))")
	public Object controlExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();

		if ("add".equals(methodName)) {
			System.out.println("Skipping method: " + methodName);
			return 0; // Custom return value
		}

		if ("divide".equals(methodName)) {
			throw new RuntimeException("Simulated exception in Around Advice");
		}

		return joinPoint.proceed();
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ControlExecutionAspect.class);
		Calculator calculator = context.getBean(Calculator.class);
		calculator.add(10, 20);
		calculator.subtract(30, 15);
		calculator.multiply(5, 4);
		calculator.divide(10, 2);
		context.close();
	}

	@Bean
	public Calculator calculator() {
		return new Calculator();
	}
}
