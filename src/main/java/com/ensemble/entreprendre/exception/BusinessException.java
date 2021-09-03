package com.ensemble.entreprendre.exception;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends ApiException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8195628765687004826L;
	
	private static HttpStatus internal = HttpStatus.BAD_REQUEST;
	
	private Collection<String> internalMessages;

	public BusinessException() {
		super(internal);
		init();
	}

	private void init() {
		internalMessages = new ArrayList<String>();
	}

	public BusinessException(HttpStatus status) {
		super(status);
		init();
	}

	public BusinessException(Throwable t, HttpStatus status) {
		super(t, status);
		init();
	}

	public BusinessException(Throwable t) {
		super(t, internal);
		init();
	}

	public boolean isNotEmpty() {
		return !this.internalMessages.isEmpty();
	}

	public BusinessException addMessage(String message) {
		this.internalMessages.add(message);
		return this;
	}
}
