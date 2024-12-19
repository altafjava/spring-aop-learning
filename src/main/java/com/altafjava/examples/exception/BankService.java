package com.altafjava.examples.exception;

import org.springframework.stereotype.Service;

@Service
public class BankService {

	public void transferMoney(int amount) {
		System.out.println("Executing transfer logic for amount: " + amount);
		if (amount > 1000) {
			throw new TransferLimitExceededException("Transfer limit exceeded!");
		}
	}

	public void withdrawMoney(int amount) {
		System.out.println("Executing withdrawal logic for amount: " + amount);
		if (amount < 0) {
			throw new NegativeAmountException("Amount cannot be negative!");
		}
	}
}
