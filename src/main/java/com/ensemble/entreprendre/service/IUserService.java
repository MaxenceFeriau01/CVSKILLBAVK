package com.ensemble.entreprendre.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.domain.Role;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;

public interface IUserService {

	void createUser(UserRequestDto userDto, Collection<Role> roles, MultipartFile cv, MultipartFile coverLetter)
			throws EntityNotFoundException, ApiNotFoundException, MessagingException, ParseException,
			ApiAlreadyExistException, org.apache.velocity.runtime.parser.ParseException, IOException, ApiException;

	UserResponseDto updateUser(Long id, UserRequestDto userDto, MultipartFile cv, MultipartFile coverLetter) throws IOException, ApiNotFoundException, ApiException;

	AuthenticationResponseDto findByEmailToAuthenticationResponseDto(String email);

	UserDetails getConnectedUser() throws ApiException;

	UserResponseDto findByEmailToUserResponseDto(String email);
}