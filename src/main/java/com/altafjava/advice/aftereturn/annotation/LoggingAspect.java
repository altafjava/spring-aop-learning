package com.altafjava.advice.aftereturn.annotation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * This is the Aspect class implementing cross-cutting concerns.
 */
@Aspect
@Component
public class LoggingAspect {

	/**
	 * Pointcut for all methods in the Calculator class.
	 */
	@Pointcut("execution(* com.altafjava.advice.Calculator.*(..))")
	public void calculatorMethods() {
	}

	/**
	 * After Returning Advice: Executed after any Calculator method returns successfully.
	 */
	@AfterReturning(pointcut = "calculatorMethods()", returning = "result")
	public void afterReturningAdvice(Object result) {
		System.out.println("After Returning Advice - Method returned: " + result);
	}
}
