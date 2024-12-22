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
public class HandleExceptionsAspect {

	@Around("execution(* com.altafjava.advice.Calculator.*(..))")
	public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (ArithmeticException ex) {
			System.err.println("Caught exception: " + ex.getMessage());

			// Suppress the exception and return a default value
			return -1;

			// Or rethrow a custom exception
//			 throw new RuntimeException("Handled exception in Around Advice", ex);
		}
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HandleExceptionsAspect.class);
		Calculator calculator = context.getBean(Calculator.class);
		calculator.add(10, 20);
		calculator.subtract(30, 15);
		calculator.multiply(5, 4);
		Integer divideResult = calculator.divide(10, 0); // Here passing zero
		System.out.println("divideResult: " + divideResult);
		context.close();
	}

	@Bean
	public Calculator calculator() {
		return new Calculator();
	}
}
