package com.altafjava.advice.before.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
	 * Before Advice: Executed before any Calculator method.
	 */
	@Before("calculatorMethods()")
	public void beforeAdvice(JoinPoint joinPoint) {
		System.out.println("Before Advice - Method " + joinPoint.getSignature().getName() + "() execution is about to start.");
	}
}
