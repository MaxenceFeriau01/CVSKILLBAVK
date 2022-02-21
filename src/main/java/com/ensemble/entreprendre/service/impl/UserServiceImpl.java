package com.ensemble.entreprendre.service.impl;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.Role;
import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.service.IMailService;
import com.ensemble.entreprendre.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	GenericConverter<User, UserResponseDto> userResponseConverter;

	@Autowired
	GenericConverter<User, AuthenticationResponseDto> authenticationResponseConverter;

	@Autowired
	GenericConverter<User, UserRequestDto> userRequestConverter;

	@Autowired
	IMailService mailService;;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email)
				.orElseThrow(() -> new AccessDeniedException("Utilisateur inconnu"));
		List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getRole().toString()))
				.collect(Collectors.toList());
		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).authorities(grantedAuthorities).build();
		return userDetails;
	}

	@Override
	public AuthenticationResponseDto findByEmail(String email) {
		return this.authenticationResponseConverter.entityToDto(this.userRepository.findByEmail(email)
				.orElseThrow(() -> new AccessDeniedException("Utilisateur inconnu")), AuthenticationResponseDto.class);
	}

	public UserRequestDto createUser(UserRequestDto newUserDto, Collection<Role> roles)
			throws EntityNotFoundException, ApiNotFoundException, MessagingException, ParseException,
			ApiAlreadyExistException, org.apache.velocity.runtime.parser.ParseException {

		Optional<User> opOldUser = this.userRepository.findByEmail(newUserDto.getEmail());
		if (opOldUser.isPresent()) {
			throw new ApiAlreadyExistException("Cette email est déjà utilisée");
		} else {
			User newUser = this.userRequestConverter.dtoToEntity(newUserDto, User.class);

			String encodedPassword = bCryptPasswordEncoder.encode(newUserDto.getPassword());
			newUser.setPassword(encodedPassword);
			newUser.setRoles(roles);

			User user = this.userRepository.save(newUser);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("firstName", user.getFirstName());
			params.put("lastName", user.getName());

			this.mailService.prepareMail(MailSubject.RegistrationConfirm, "Confirmation d'inscription", user.getEmail(),
					params, null);

			return this.userRequestConverter.entityToDto(user, UserRequestDto.class);
		}

	}

}
