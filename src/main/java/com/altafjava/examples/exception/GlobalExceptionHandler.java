package com.altafjava.examples.exception;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NegativeAmountException.class)
	public void handleNegativeAmountException(NegativeAmountException ex) {
		System.out.println("Global Exception handler: NegativeAmountException - " + ex.getMessage());
	}

	@ExceptionHandler(TransferLimitExceededException.class)
	public void handleTransferLimitExceededException(TransferLimitExceededException ex) {
		System.out.println("Global Exception handler: TransferLimitExceededException - " + ex.getMessage());
	}
}
