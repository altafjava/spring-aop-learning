package com.altafjava.examples.transaction.programmatic;

import org.springframework.aop.framework.ProxyFactory;

public class Test {
	public static void main(String[] args) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new BankService());
		proxyFactory.addAdvice(new TransactionAspect());

		BankService proxyBankService = (BankService) proxyFactory.getProxy();

		// Successful transaction
		try {
			proxyBankService.transferMoney(500);
		} catch (Exception ignored) {
		}
		System.out.println("-------");
		// Failed transaction
		try {
			proxyBankService.transferMoney(1500);
		} catch (Exception ignored) {
		}
	}
}
