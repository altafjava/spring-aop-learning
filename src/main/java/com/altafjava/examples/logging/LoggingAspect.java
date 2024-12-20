package com.altafjava.examples.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Pointcut("@annotation(com.altafjava.examples.logging.LogExecution)")
	public void logExecutionPointcut() {
	}

	@Pointcut("@annotation(com.altafjava.examples.logging.MonitorPerformance)")
	public void monitorPerformancePointcut() {
	}

	@Around("logExecutionPointcut()")
	public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("Log entry method: " + methodName + "()");
		Object result = joinPoint.proceed();
		System.out.println("Log exit method: " + methodName + "()\n");
		return result;
	}

	@Around("monitorPerformancePointcut()")
	public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		String methodName = joinPoint.getSignature().getName();
		System.out.println("Monitor: Method " + methodName + "() executed in " + (endTime - startTime) + " ms");
		return result;
	}
}