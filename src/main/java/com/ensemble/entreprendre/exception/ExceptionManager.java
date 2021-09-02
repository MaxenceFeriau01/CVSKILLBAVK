package com.ensemble.entreprendre.exception;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ensemble.entreprendre.dto.ApiExceptionResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionManager {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiExceptionResponse> manageException(final Exception e) {
				return this.getExceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiExceptionResponse> manageIllegalArgumentException(final RuntimeException e) {
		return this.getExceptionResponse(e, HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<ApiExceptionResponse> manageAccessRefusedException(final RuntimeException e) {
		return this.getExceptionResponse(e, HttpStatus.FORBIDDEN, e.getMessage());
	}

//	@ExceptionHandler(value = InternalAuthenticationServiceException.class)
//	public ResponseEntity<ApiExceptionResponse> manageAccessDeniedException(final RuntimeException e) {
//		return this.getExceptionResponse(e, HttpStatus.UNAUTHORIZED, e.getMessage());
//	}


	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity<ApiExceptionResponse> manageApiException(final ApiException e) {
		return this.getExceptionResponse(e, e.getStatus(), e.getMessage());
	}

	private ResponseEntity<ApiExceptionResponse> getExceptionResponse(final Exception exception,
			final HttpStatus status, final String message) {
		ApiExceptionResponse error = new ApiExceptionResponse();
		error.setStatus(status.value());
		error.setMessage(message);
		Arrays.asList(exception.getStackTrace()).stream().map(StackTraceElement::toString).forEach(ExceptionManager.log::info);
		ExceptionManager.log.info(exception.getMessage());
		
		return new ResponseEntity<>(error, status);
	}

}
