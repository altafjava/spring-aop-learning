package com.altafjava.examples.exception;

public class NegativeAmountException extends RuntimeException {

	private static final long serialVersionUID = 6812728815240902775L;

	public NegativeAmountException(String message) {
		super(message);
	}
}
