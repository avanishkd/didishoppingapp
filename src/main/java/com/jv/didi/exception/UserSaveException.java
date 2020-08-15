package com.jv.didi.exception;

public class UserSaveException extends RuntimeException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -9030809728894612878L;

	public UserSaveException() {
		super();
	}

	public UserSaveException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserSaveException(final String message) {
		super(message);
	}

	public UserSaveException(final Throwable cause) {
		super(cause);
	}

}
