package com.epam.mbank.exception;

public class NeverRemovedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NeverRemovedException() {
		super();
	}

	public NeverRemovedException(String message) {
		super(message);
	}

	public NeverRemovedException(Throwable cause) {
		super(cause);
	}

	public NeverRemovedException(String message, Throwable cause) {
		super(message, cause);
	}

}
