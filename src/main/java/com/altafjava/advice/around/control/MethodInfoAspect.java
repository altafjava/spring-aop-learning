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
public class MethodInfoAspect {

	@Around("execution(* com.altafjava.advice.Calculator.*(..))")
	public Object accessMethodInfo(ProceedingJoinPoint joinPoint) throws Throwable {
		// Static Information
		System.out.println("Method Name: " + joinPoint.getSignature().getName());
		System.out.println("Declaring Class: " + joinPoint.getSignature().getDeclaringTypeName());

		// Dynamic Information
		Object[] args = joinPoint.getArgs();
		System.out.println("Method Arguments: " + java.util.Arrays.toString(args));

		return joinPoint.proceed();
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MethodInfoAspect.class);
		Calculator calculator = context.getBean(Calculator.class);
		calculator.add(10, 20);
		calculator.subtract(30, 15);
		calculator.multiply(5, 4);
		try {
			calculator.divide(10, 0);
		} catch (Exception ignored) {
		}
		context.close();
	}

	@Bean
	public Calculator calculator() {
		return new Calculator();
	}
}