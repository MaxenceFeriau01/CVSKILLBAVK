package com.ensemble.entreprendre.service;

import java.text.ParseException;
import java.util.Collection;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;

import com.ensemble.entreprendre.domain.Role;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;

public interface IUserService {

	UserRequestDto createUser(UserRequestDto useDto, Collection<Role> roles)
			throws EntityNotFoundException, ApiNotFoundException, MessagingException, ParseException,
			ApiAlreadyExistException, org.apache.velocity.runtime.parser.ParseException;

	AuthenticationResponseDto findByEmail(String email);

	UserDetails getConnectedUser() throws ApiException;
}