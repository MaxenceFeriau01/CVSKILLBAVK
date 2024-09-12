package com.ensemble.entreprendre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
	private Long userId;
	private String password;
}
