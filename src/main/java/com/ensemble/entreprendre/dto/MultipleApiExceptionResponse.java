package com.ensemble.entreprendre.dto;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleApiExceptionResponse extends ApiExceptionResponse{

	private Collection<String> internalMessages;
}
