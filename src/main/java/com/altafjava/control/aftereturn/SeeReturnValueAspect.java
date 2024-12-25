package com.altafjava.control.aftereturn;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
public class SeeReturnValueAspect {

	@AfterReturning(pointcut = "execution(* com.altafjava.advice.Calculator.*(..))", returning = "result")
	public void seeReturnValue(JoinPoint joinPoint, Object result) throws Throwable {
		System.out.println("Original Result Value: " + result);
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SeeReturnValueAspect.class);
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
