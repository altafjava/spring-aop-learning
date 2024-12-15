package com.altafjava.examples.transaction.programmatic;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TransactionAspect implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		System.out.println("Transaction started for method: " + methodInvocation.getMethod().getName());
		try {
			Object result = methodInvocation.proceed(); // Proceed with the target method
			System.out.println("Transaction committed for method: " + methodInvocation.getMethod().getName());
			return result;
		} catch (Exception e) {
			System.out.println("Transaction rolled back due to: " + e.getMessage());
			throw e;
		}
	}
}
