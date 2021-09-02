package com.ensemble.entreprendre.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends Exception {

	private static final long serialVersionUID = 2494189768200677730L;
	
	private final HttpStatus status;
	
	public ApiException(final String message, final Throwable t, final HttpStatus status) {
		super(message, t);
		this.status = status;
	}

	public ApiException(final String message, final Throwable t) {
		super(message, t);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiException(final String message, final HttpStatus status) {
		super(message);
		this.status = status;
	}

	public ApiException(final String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiException(final Throwable t, final HttpStatus status) {
		super(t);
		this.status = status;
	}

	public ApiException(final Throwable t) {
		super(t);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiException(final HttpStatus status) {
		super();
		this.status = status;
	}

	public ApiException() {
		super();
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
