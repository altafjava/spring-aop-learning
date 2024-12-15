package com.altafjava.examples.transaction;

public class TransactionManager {
	public void startTransaction() {
		System.out.println("Transaction started.");
	}

	public void commitTransaction() {
		System.out.println("Transaction committed.");
	}

	public void rollbackTransaction() {
		System.out.println("Transaction rolled back.");
	}
}
