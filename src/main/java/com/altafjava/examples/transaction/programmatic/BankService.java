package com.altafjava.examples.transaction.programmatic;

public class BankService {
	public void transferMoney(int amount) {
		System.out.println("Executing transferMoney logic for amount: " + amount);
		if (amount > 1000) {
			throw new RuntimeException("Transfer limit exceeded!");
		}
	}
}
