package com.epam.mbank.exception;

public class NoSuchItem extends Exception {
	private static final long serialVersionUID = 1L;

	public NoSuchItem() {
		super();
	}

	public NoSuchItem(String message) {
		super(message);
	}

	public NoSuchItem(Throwable t) {
		super(t);
	}

	public NoSuchItem(String message, Throwable t) {
		super(message, t);
	}

}
