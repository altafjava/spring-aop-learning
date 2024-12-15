package com.altafjava.examples.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAspect {

	private final TransactionManager transactionManager;

	public TransactionAspect(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Around("@annotation(com.altafjava.examples.transaction.Transactional)")
	public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		transactionManager.startTransaction();
		try {
			Object result = joinPoint.proceed(); // Proceed with the method
			transactionManager.commitTransaction();
			return result;
		} catch (Exception e) {
			transactionManager.rollbackTransaction();
			throw e;
		}
	}
}
