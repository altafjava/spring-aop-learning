package com.altafjava.advice.before;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * This is the Aspect class implementing cross-cutting concerns.
 */
public class LoggingAspect implements MethodBeforeAdvice {

	/**
	 * Before Advice: Executed before the method call.
	 */
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("Before Advice: " + method.getName() + ", Args: " + Arrays.toString(args));
	}
}
