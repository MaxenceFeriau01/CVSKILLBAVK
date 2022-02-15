package com.ensemble.entreprendre.service.impl;

import java.text.ParseException;
import java.util.Collection;
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
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.repository.IUserRepository;
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
	GenericConverter<User, UserRequestDto> userRequestConverter;

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
	public UserResponseDto findByEmail(String email) {
		return this.userResponseConverter.entityToDto(this.userRepository.findByEmail(email).orElseThrow(()->new AccessDeniedException("Utilisateur inconnu")),UserResponseDto.class);
	}
	
	public UserRequestDto createUser(UserRequestDto newUserDto, Collection<Role> roles) throws EntityNotFoundException, ApiNotFoundException,
			MessagingException, ParseException, ApiAlreadyExistException {

		Optional<User> opOldUser = this.userRepository.findByEmail(newUserDto.getEmail());
		if (opOldUser.isPresent()) {
			throw new ApiAlreadyExistException("Cette email est déjà utilisée");
		} else {
			User newUser = this.userRequestConverter.dtoToEntity(newUserDto, User.class);

			String encodedPassword = bCryptPasswordEncoder.encode(newUserDto.getPassword());
			newUser.setPassword(encodedPassword);
			newUser.setRoles(roles);
			// we must save before sending mail
			// TODO mail
			User user = this.userRepository.save(newUser);
			/*
			 * Set<Role> collectedRoles = newUserDto.getRoles().stream().map(role -> {
			 * UserRole uRole = new UserRole(); uRole.setUser(user); uRole.setRole(role);
			 * return uRole; }).collect(Collectors.toList());
			 */
			/*
			 * List<UserRole> roles = this.userRoleRepository.saveAll(collectedRoles);
			 * HashMap<String,String> params = new HashMap<String,String>();
			 * params.put("password", password); params.put("firstName",
			 * user.getFirstName()); params.put("lastName", user.getLastName());
			 * Collection<Resource> attachments = new ArrayList<Resource>();
			 * Optional<Collection<Resource>> opAttachments = Optional.of(attachments);
			 * this.mailService.prepareMail(MailSubject.Password,"Bienvenue",
			 * newUser.getEmail(), params, opAttachments);
			 * 
			 * user.setUserRoles(roles);
			 */
			return this.userRequestConverter.entityToDto(user, UserRequestDto.class);
		}

	}

}
