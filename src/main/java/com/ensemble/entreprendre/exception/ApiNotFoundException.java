package com.ensemble.entreprendre.exception;

import org.springframework.http.HttpStatus;

public class ApiNotFoundException extends ApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3369268324201957575L;
	
	private static HttpStatus internal = HttpStatus.NOT_FOUND;

	public ApiNotFoundException() {
		super(internal);
	}

	public ApiNotFoundException(HttpStatus status) {
		super(status);
	}

	public ApiNotFoundException(String message, HttpStatus status) {
		super(message, status);
	}

	public ApiNotFoundException(String message, Throwable t, HttpStatus status) {
		super(message, t, status);
	}

	public ApiNotFoundException(String message, Throwable t) {
		super(message, t, internal);
	}

	public ApiNotFoundException(String message) {
		super(message, internal);
	}

	public ApiNotFoundException(Throwable t, HttpStatus status) {
		super(t, status);
	}

	public ApiNotFoundException(Throwable t) {
		super(t, internal);
	}

	
}
