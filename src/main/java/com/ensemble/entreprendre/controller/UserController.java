package com.ensemble.entreprendre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.CredentialsDto;
import com.ensemble.entreprendre.dto.UserDto;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.security.helper.JwtTokenUtilBean;

@RequestMapping(path = "/users")
@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtilBean jwtTokenUtilBean;
	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping(path = "/authenticate")
	public UserDto createAuthenticationToken(@RequestBody CredentialsDto authenticationRequest)
			throws ApiNotFoundException {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		UserDto customer = new UserDto();
		customer.setEmail(authenticationRequest.getEmail());
		customer.setToken(jwtTokenUtilBean.generateToken(userDetails));
		return customer;
	}

	@PostMapping(path = "/registration")
	public UserDto registration(@RequestBody UserDto useDto) throws ApiNotFoundException {
		return useDto;

	}

	private void authenticate(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}

}