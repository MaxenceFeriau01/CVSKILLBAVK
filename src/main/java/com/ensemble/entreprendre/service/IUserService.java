package com.ensemble.entreprendre.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.domain.Role;
import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.dto.UserStatDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.filter.BasicDtoFilter;
import com.ensemble.entreprendre.filter.IndividualAnalysisUserDtoFilter;
import com.ensemble.entreprendre.filter.UserDtoFilter;

public interface IUserService {

	void createUser(UserRequestDto userDto, Collection<Role> roles, MultipartFile cv, MultipartFile coverLetter)
			throws EntityNotFoundException, ApiNotFoundException, MessagingException, ParseException,
			ApiAlreadyExistException, org.apache.velocity.runtime.parser.ParseException, IOException, ApiException;

	UserResponseDto updateUser(Long id, UserRequestDto userDto, MultipartFile cv, MultipartFile coverLetter)
			throws IOException, ApiNotFoundException, ApiException;

	AuthenticationResponseDto findByEmailToAuthenticationResponseDto(String email);

	User findByEmail(String email) throws ApiNotFoundException;

	UserResponseDto findByEmailToUserResponseDto(String email);

	Page<UserResponseDto> getAll(Pageable pageable, UserDtoFilter filter);

	Page<UserResponseDto> getAllIndividualAnalysis(Pageable pageable, IndividualAnalysisUserDtoFilter filter);

	void active(long id, boolean activated) throws ApiException;

	void delete(long id) throws ApiException;

	UserResponseDto getById(long id) throws ApiNotFoundException;

	void updateResetPasswordToken(String email) throws ApiNotFoundException, EntityNotFoundException,
			MessagingException, org.apache.velocity.runtime.parser.ParseException;

	User getByResetPasswordToken(String token) throws ApiNotFoundException;

	void updatePassword(Long id, String newPassword) throws ApiNotFoundException;

	List<Long> getAppliedCompanies(String email);

    AuthenticationResponseDto authenticate(String email, String password) throws ApiException;

    Page<UserStatDto> getUserStats(Pageable pageable);

}