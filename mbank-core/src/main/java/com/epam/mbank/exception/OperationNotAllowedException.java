package com.epam.mbank.exception;

public class OperationNotAllowedException extends Exception {
	private static final long serialVersionUID = 1L;

	public OperationNotAllowedException() {
		super();
	}

	public OperationNotAllowedException(String message) {
		super(message);
	}

	public OperationNotAllowedException(Throwable t, String message) {
		super(message, t);
	}

	public OperationNotAllowedException(Throwable t) {
		super(t);
	}
}
