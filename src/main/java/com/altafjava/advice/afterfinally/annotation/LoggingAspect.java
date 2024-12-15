package com.altafjava.advice.afterfinally.annotation;

import org.aspectj.lang.annotation.After;
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
	 * After (Finally) Advice: Executed after any Calculator method completes (normal or exceptional).
	 */
	@After("calculatorMethods()")
	public void afterAdvice() {
		System.out.println("After (Finally) Advice - Method execution completed.");
	}
}
