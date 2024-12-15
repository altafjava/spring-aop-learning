package com.altafjava.advice.around.annotation;

import org.aspectj.lang.annotation.Around;
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
	 * Around Advice: Executed before and after the Calculator method call.
	 */
	@Around("calculatorMethods()")
	public Object aroundAdvice(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("Around Advice - Before: " + joinPoint.getSignature());
		Object result = joinPoint.proceed();
		System.out.println("Around Advice - After: " + joinPoint.getSignature());
		return result;
	}
}
