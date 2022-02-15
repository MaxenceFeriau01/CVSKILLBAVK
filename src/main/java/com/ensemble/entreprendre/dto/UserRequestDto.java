package com.ensemble.entreprendre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

	private String email;

	private String phone;
	
	private String name;
	
	private String firstName;
	
	private String token;
	
	private String password;
}
