package com.ensemble.entreprendre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDto {


	private String name;

	private String firstName;

	private String token;
}
