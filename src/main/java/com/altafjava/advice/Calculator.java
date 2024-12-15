package com.altafjava.advice;

import org.springframework.stereotype.Component;

/**
 * This is the Target class. 
 * Methods add(), multiply(), subtract(), and divide() are Joinpoints.
 */
@Component
public class Calculator {

	public int add(int a, int b) {
		System.out.println("Inside Calculator.add(): a=" + a + " b=" + b);
		return a + b;
	}

	public int subtract(int a, int b) {
		System.out.println("Inside Calculator.subtract(): a=" + a + " b=" + b);
		return a - b;
	}

	public int multiply(int a, int b) {
		System.out.println("Inside Calculator.multiply(): a=" + a + " b=" + b);
		return a * b;
	}

	public int divide(int a, int b) throws ArithmeticException {
		System.out.println("Inside Calculator.divide(): a=" + a + " b=" + b);
		if (b == 0)
			throw new ArithmeticException("Cannot divide by zero");
		return a / b;
	}
}
