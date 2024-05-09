package com.csmtech.exceptions;

public class SessionNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public SessionNotFoundException(String message) {
		super(message);
	}
}
