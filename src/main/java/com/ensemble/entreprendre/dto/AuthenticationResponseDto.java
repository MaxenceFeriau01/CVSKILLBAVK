package com.ensemble.entreprendre.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDto {

	private String email;
	
	private String name;
	
	private String firstName;

	private Set<RoleDto> roles;
	
	private String token;
}
