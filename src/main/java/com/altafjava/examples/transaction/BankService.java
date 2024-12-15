package com.altafjava.examples.transaction;

import org.springframework.stereotype.Service;

@Service
public class BankService {

	@Transactional
	public void transferMoney(int amount) {
		System.out.println("Executing transfer logic for amount: " + amount);
		if (amount > 1000) {
			throw new RuntimeException("Transfer limit exceeded!");
		}
	}
}
