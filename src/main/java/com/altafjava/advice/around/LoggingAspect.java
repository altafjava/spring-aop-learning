package com.altafjava.advice.around;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * This is the Aspect class implementing cross-cutting concerns.
 */
public class LoggingAspect implements MethodInterceptor {

	/**
	 * Around Advice: Executed before and after the method call.
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		System.out.println("Around - Before: " + methodInvocation.getMethod().getName());
		Object result = methodInvocation.proceed();
		System.out.println("Around - After: " + methodInvocation.getMethod().getName());
		return result;
	}
}
