package com.ensemble.entreprendre.controller;

import java.text.ParseException;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.dto.CredentialsDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.repository.IRoleRepository;
import com.ensemble.entreprendre.security.helper.JwtTokenUtilBean;
import com.ensemble.entreprendre.service.IUserService;

@RequestMapping(path = "/api/users")
@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtilBean jwtTokenUtilBean;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	

	@PostMapping(path = "/authenticate")
	public UserResponseDto createAuthenticationToken(@RequestBody CredentialsDto authenticationRequest)
			throws ApiException {
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		}
		catch (BadCredentialsException e) {
			throw new ApiException("Identifiants incorrectes",HttpStatus.UNAUTHORIZED);
		}
		
		UserResponseDto user = userService.findByEmail(authenticationRequest.getEmail());
		user.setToken(jwtTokenUtilBean.generateToken(userDetails));
		return user;
	}
	/**
	 * 
	 * Used to create a User with a USER role
	 * @param useDto
	 * @return
	 * @throws ApiNotFoundException
	 * @throws EntityNotFoundException
	 * @throws MessagingException
	 * @throws ParseException
	 * @throws ApiAlreadyExistException
	 */

	@PostMapping(path = "/register")
	public UserRequestDto registration(@RequestBody UserRequestDto useDto) throws ApiNotFoundException, EntityNotFoundException,
			MessagingException, ParseException, ApiAlreadyExistException {
		return this.userService.createUser(useDto, Arrays.asList(roleRepository.findByRole(RoleEnum.USER)));

	}
}