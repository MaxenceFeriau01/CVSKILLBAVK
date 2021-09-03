package com.ensemble.entreprendre.exception;

import org.springframework.http.HttpStatus;

public class TechnicalException extends ApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8703236495579285588L;
	
	private static HttpStatus internal = HttpStatus.BAD_REQUEST;

	public TechnicalException() {
		super(internal);
	}

	public TechnicalException(HttpStatus status) {
		super(status);
	}

	public TechnicalException(String message, HttpStatus status) {
		super(message, status);
	}

	public TechnicalException(String message, Throwable t, HttpStatus status) {
		super(message, t, status);
	}

	public TechnicalException(String message, Throwable t) {
		super(message, t, internal);
	}

	public TechnicalException(String message) {
		super(message, internal);
	}

	public TechnicalException(Throwable t, HttpStatus status) {
		super(t, status);
	}

	public TechnicalException(Throwable t) {
		super(t, internal);
	}
}
