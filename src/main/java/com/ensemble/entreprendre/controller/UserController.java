package com.ensemble.entreprendre.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.CredentialsDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.repository.IRoleRepository;
import com.ensemble.entreprendre.security.helper.JwtTokenUtilBean;
import com.ensemble.entreprendre.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping(path = "/api/users")
@RestController
public class UserController {

	public static final String ACCEPTED_FILE_FORMAT = "application/pdf";

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
	@Autowired
	ObjectMapper objectMapper;

	@PostMapping(path = "/authenticate")
	public AuthenticationResponseDto createAuthenticationToken(@RequestBody CredentialsDto authenticationRequest)
			throws ApiException {

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new ApiException("Identifiants incorrectes", HttpStatus.UNAUTHORIZED);
		}

		AuthenticationResponseDto user = userService.findByEmail(authenticationRequest.getEmail());
		user.setToken(jwtTokenUtilBean.generateToken(userDetails));
		return user;
	}

	/**
	 * Get the roles of the connected user
	 * 
	 * @return roles
	 * @throws ApiException
	 */
	@GetMapping(path = "/self/roles")
	public String[] getConnectedUserRoles() throws ApiException {
		return userService.getConnectedUser().getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.toArray(String[]::new);
	}

	/**
	 * Get the connected
	 * 
	 * @return userResponseDto
	 * @throws ApiException
	 */
	@GetMapping(path = "/self")
	public UserResponseDto getConnectedUser() throws ApiException {
		return null;

	}

	/**
	 * 
	 * Used to create a User with a USER role
	 * 
	 * @param useDto
	 * @return
	 * @throws EntityNotFoundException
	 * @throws MessagingException
	 * @throws ParseException
	 * @throws ApiException
	 * @throws IOException
	 * @throws org.apache.velocity.runtime.parser.ParseException
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/register")
	public void registration(@RequestPart("user") String user,
			@RequestPart(value = "cv", required = false) MultipartFile cv,
			@RequestPart(value = "coverLetter", required = false) MultipartFile coverLetter)
			throws EntityNotFoundException, MessagingException, ParseException, ApiException, IOException,
			org.apache.velocity.runtime.parser.ParseException {

		UserRequestDto toCreate = objectMapper.readValue(user, UserRequestDto.class);
		if (cv != null) {
			if (!cv.getContentType().equals(ACCEPTED_FILE_FORMAT)) {
				throw new ApiException("Le CV doit respecter le format pdf", HttpStatus.BAD_REQUEST);
			}
			toCreate.setCv(cv.getBytes());
		}
		if (coverLetter != null) {
			if (!coverLetter.getContentType().equals(ACCEPTED_FILE_FORMAT)) {
				throw new ApiException("La lettre de motivation doit respecter le format pdf", HttpStatus.BAD_REQUEST);
			}
			toCreate.setCoverLetter(coverLetter.getBytes());

		}
		this.userService.createUser(toCreate, Arrays.asList(roleRepository.findByRole(RoleEnum.ROLE_USER)));

	}
}