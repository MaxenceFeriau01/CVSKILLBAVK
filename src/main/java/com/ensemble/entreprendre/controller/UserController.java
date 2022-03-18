package com.ensemble.entreprendre.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.dto.CredentialsDto;
import com.ensemble.entreprendre.dto.ForgotPasswordDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.UserDtoFilter;
import com.ensemble.entreprendre.repository.IRoleRepository;
import com.ensemble.entreprendre.security.helper.JwtTokenUtilBean;
import com.ensemble.entreprendre.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping(path = "/api/users")
@RestController
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
	@Autowired
	ObjectMapper objectMapper;

	@ApiOperation(value = "User getAll endpoint", response = CompanyDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"), })
	@GetMapping("/search")
	public Page<UserResponseDto> getAll(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params") @PageableDefault(sort = {
					"id" }, direction = Sort.Direction.ASC) Pageable pageable,
			UserDtoFilter filter) {
		return this.userService.getAll(pageable, filter);
	}

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

		AuthenticationResponseDto user = userService
				.findByEmailToAuthenticationResponseDto(authenticationRequest.getEmail());
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
		var userDetails = userService.getConnectedUser();
		return userService.findByEmailToUserResponseDto(userDetails.getUsername());

	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(path = "/{id}")
	public UserResponseDto updateUser(@PathVariable(name = "id") long id, @RequestPart("user") String user,
			@RequestPart(value = "cv", required = false) MultipartFile cv,
			@RequestPart(value = "coverLetter", required = false) MultipartFile coverLetter)
			throws IOException, ApiException {
		UserRequestDto toUpdate = objectMapper.readValue(user, UserRequestDto.class);

		return this.userService.updateUser(id, toUpdate, cv, coverLetter);
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

		this.userService.createUser(toCreate, Arrays.asList(roleRepository.findByRole(RoleEnum.ROLE_USER)), cv,
				coverLetter);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/forgot-password")
	public void forgotPassword(@RequestBody ForgotPasswordDto forgotPassword) {

		
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/reset-password")
	public void processResetPassword(@RequestBody ForgotPasswordDto forgotPassword) {

		
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@GetMapping(path = "{token}/reset-password")
	public void showResetPassword(@PathVariable String token) {

		
	}

	@GetMapping(path = "/{id}")
	@Secured({ "ROLE_ADMIN" })
	@ResponseStatus(HttpStatus.OK)
	public UserResponseDto getById(@PathVariable(name = "id") long id) throws ApiException {
		return this.userService.getById(id);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured({ "ROLE_ADMIN" })
	public void delete(@PathVariable(name = "id") long id) throws ApiException {
		this.userService.delete(id);
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "{id}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void active(@PathVariable(name = "id") long id, @RequestBody UserDtoFilter userDtoFilter)
			throws ApiException, EntityNotFoundException, MessagingException, ParseException, IOException {

		this.userService.active(id, userDtoFilter.isActivated());
	}
}