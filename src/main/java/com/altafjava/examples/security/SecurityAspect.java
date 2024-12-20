package com.altafjava.examples.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

	@Around("@annotation(preAuthorize)")
	public Object checkPreAuthorize(ProceedingJoinPoint joinPoint, PreAuthorize preAuthorize) throws Throwable {
		String role = preAuthorize.value();
		if (!SecurityContext.hasRole(role)) {
			throw new SecurityException("Access Denied: Missing role - " + role);
		}
		return joinPoint.proceed();
	}

	@Around("@annotation(postAuthorize)")
	public Object checkPostAuthorize(ProceedingJoinPoint joinPoint, PostAuthorize postAuthorize) throws Throwable {
		Object result = joinPoint.proceed();
		String condition = postAuthorize.value();
		if (!SecurityContext.evaluateCondition(condition, result)) {
			throw new SecurityException("Access Denied: Post-condition failed");
		}
		return result;
	}
}