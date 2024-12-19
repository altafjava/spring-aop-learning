package com.altafjava.examples.exception;

public class TransferLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 6601107078166008025L;

	public TransferLimitExceededException(String message) {
		super(message);
	}
}
