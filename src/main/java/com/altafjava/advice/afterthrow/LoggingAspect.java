package com.altafjava.advice.afterthrow;

import java.lang.reflect.Method;
import org.springframework.aop.ThrowsAdvice;

/**
 * This is the Aspect class implementing cross-cutting concerns.
 */
public class LoggingAspect implements ThrowsAdvice {

	/**
	 * After Throwing Advice: Executed if the method throws an exception.
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
		System.out.println("After Throwing Advice: " + method.getName() + ", Exception: " + ex.getMessage());
	}
}
