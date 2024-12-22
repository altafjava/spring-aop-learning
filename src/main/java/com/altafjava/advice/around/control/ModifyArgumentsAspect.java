package com.altafjava.advice.around.control;

import java.util.Arrays;
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
public class ModifyArgumentsAspect {

	@Around("execution(* com.altafjava.advice.Calculator.*(..))")
	public Object modifyArguments(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		System.out.println("Original Argument: " + Arrays.toString(args));
		
		if (args != null && args.length > 0 && args[0] instanceof Integer) {
			args[0] = (Integer) args[0] + 1; // Add 1 to the first argument
		}
		System.out.println("Modified Argument: " + Arrays.toString(args));
		return joinPoint.proceed(args);
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModifyArgumentsAspect.class);
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
