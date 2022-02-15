package com.ensemble.entreprendre.exception;

import org.springframework.http.HttpStatus;

public class ApiAlreadyExistException  extends ApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3369268324201957575L;
	
	private static HttpStatus internal = HttpStatus.NOT_FOUND;

	public ApiAlreadyExistException() {
		super(internal);
	}

	public ApiAlreadyExistException(HttpStatus status) {
		super(status);
	}

	public ApiAlreadyExistException(String message, HttpStatus status) {
		super(message, status);
	}

	public ApiAlreadyExistException(String message, Throwable t, HttpStatus status) {
		super(message, t, status);
	}

	public ApiAlreadyExistException(String message, Throwable t) {
		super(message, t, internal);
	}

	public ApiAlreadyExistException(String message) {
		super(message, internal);
	}

	public ApiAlreadyExistException(Throwable t, HttpStatus status) {
		super(t, status);
	}

	public ApiAlreadyExistException(Throwable t) {
		super(t, internal);
	}

}

