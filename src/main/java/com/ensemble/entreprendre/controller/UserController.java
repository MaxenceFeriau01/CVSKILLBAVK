package com.ensemble.entreprendre.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.User_;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.dto.CredentialsDto;
import com.ensemble.entreprendre.dto.ForgotPasswordDto;
import com.ensemble.entreprendre.dto.ResetPasswordDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.filter.UserDtoFilter;
import com.ensemble.entreprendre.repository.IRoleRepository;
import com.ensemble.entreprendre.service.IConnectedUserService;
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
	private IUserService userService;
	@Autowired
	private IConnectedUserService connectedUserService;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	ObjectMapper objectMapper;

	@ApiOperation(value = "User getAll endpoint", response = UserResponseDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"), })
	@GetMapping("/search")
	public Page<UserResponseDto> getAll(@ApiIgnore() Pageable pageable, UserDtoFilter filter) {
		if (filter.getExport()) {
			return this.userService.getAll(Pageable.unpaged(), filter);
		}
		return this.userService.getAll(pageable, filter);
	}

	@PostMapping(path = "/authenticate")
	public AuthenticationResponseDto createAuthenticationToken(@RequestBody CredentialsDto authenticationRequest)
			throws ApiException {
		return this.userService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
	}

	/**
	 * Get the roles of the connected user
	 * 
	 * @return roles
	 * @throws ApiException
	 */
	@GetMapping(path = "/self/roles")
	public String[] getConnectedUserRoles() throws ApiException {
		return connectedUserService.getConnectedUserRoles();
	}

	/**
	 * Get the connected user
	 * 
	 * @return userResponseDto
	 * @throws ApiException
	 */
	@GetMapping(path = "/self")
	public UserResponseDto getConnectedUser() throws ApiException {
		var userDetails = connectedUserService.getConnectedUser();
		return userService.findByEmailToUserResponseDto(userDetails.getUsername());

	}

	/**
	 * Set last modified date to now for current user
	 *
	 * @throws ApiException
	 */
	@PostMapping(path = "/self/no-profile-update")
	public void patchNoProfileUpdate() throws ApiException {
		userService.setLastModifiedDateToNow();
	}

	/**
	 * Get applied companies of the connected user
	 * 
	 * @return id of applied companies
	 * @throws ApiException
	 */
	@GetMapping(path = "/self/applied-companies")
	public List<Long> appliedCompanies() throws ApiException {
		var userDetails = connectedUserService.getConnectedUser();
		return userService.getAppliedCompanies(userDetails.getUsername());
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
	 * @throws URISyntaxException 
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/register")
	public void registration(@RequestPart("user") String user,
			@RequestPart(value = "cv", required = false) MultipartFile cv,
			@RequestPart(value = "coverLetter", required = false) MultipartFile coverLetter)
			throws EntityNotFoundException, MessagingException, ParseException, ApiException, IOException,
			org.apache.velocity.runtime.parser.ParseException, URISyntaxException {

		UserRequestDto toCreate = objectMapper.readValue(user, UserRequestDto.class);

		this.userService.createUser(toCreate, Arrays.asList(roleRepository.findByRole(RoleEnum.ROLE_USER)), cv,
				coverLetter);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/forgot-password")
	public void forgotPassword(@RequestBody ForgotPasswordDto forgotPassword) throws ApiNotFoundException,
			EntityNotFoundException, MessagingException, org.apache.velocity.runtime.parser.ParseException {
		this.userService.updateResetPasswordToken(forgotPassword.getEmail());

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/reset-password")
	public void processResetPassword(@RequestBody ResetPasswordDto resetPassword) throws ApiNotFoundException {
		this.userService.updatePassword(resetPassword.getUserId(), resetPassword.getPassword());
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "{token}/reset-password")
	public Long showResetPassword(@PathVariable String token) throws ApiNotFoundException {
		User user = this.userService.getByResetPasswordToken(token);
		return user.getId();
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

		this.userService.active(id, userDtoFilter.getActivated());
	}
}
