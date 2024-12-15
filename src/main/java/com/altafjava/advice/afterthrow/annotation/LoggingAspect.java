package com.altafjava.advice.afterthrow.annotation;

import org.aspectj.lang.annotation.AfterThrowing;
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
	 * After Throwing Advice: Executed if a Calculator method throws an exception.
	 */
	@AfterThrowing(pointcut = "calculatorMethods()", throwing = "ex")
	public void afterThrowingAdvice(Exception ex) {
		System.out.println("After Throwing Advice - Exception: " + ex.getMessage());
	}
}
