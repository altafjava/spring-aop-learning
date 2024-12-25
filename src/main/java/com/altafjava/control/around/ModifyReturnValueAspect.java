package com.altafjava.control.around;

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
public class ModifyReturnValueAspect {

	@Around("execution(* com.altafjava.advice.Calculator.*(..))")
	public Object modifyReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = joinPoint.proceed();
		System.out.println("Original Result Value: " + result);
		if (result instanceof Integer) {
			result = (Integer) result + 100; // Add 100 to the result
		}

		System.out.println("Modified Result Value: " + result);
		return result;
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModifyReturnValueAspect.class);
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
