package com.ensemble.entreprendre.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.FileDb;
import com.ensemble.entreprendre.domain.Role;
import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.enumeration.FileTypeEnum;
import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.service.IMailService;
import com.ensemble.entreprendre.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {

	public static final String ACCEPTED_FILE_FORMAT = "application/pdf";

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
	IMailService mailService;

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
	public AuthenticationResponseDto findByEmailToAuthenticationResponseDto(String email) {
		return this.authenticationResponseConverter.entityToDto(this.userRepository.findByEmail(email)
				.orElseThrow(() -> new AccessDeniedException("Utilisateur inconnu")), AuthenticationResponseDto.class);
	}

	@Override
	public UserResponseDto findByEmailToUserResponseDto(String email) {
		User user = this.userRepository.findByEmail(email)
				.orElseThrow(() -> new AccessDeniedException("Utilisateur inconnu"));
		return this.userResponseConverter.entityToDto(user, UserResponseDto.class);
	}

	public UserDetails getConnectedUser() throws ApiException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new ApiException("Utilisateur non connecté", HttpStatus.UNAUTHORIZED);
		}
		Object principal = authentication.getPrincipal();
		if (principal != null && principal instanceof UserDetails) {
			return (UserDetails) principal;
		}
		throw new ApiException("Utilisateur non connecté", HttpStatus.UNAUTHORIZED);

	}

	@Override
	public void createUser(UserRequestDto newUserDto, Collection<Role> roles, MultipartFile cv,
			MultipartFile coverLetter) throws EntityNotFoundException, MessagingException, ParseException,
			org.apache.velocity.runtime.parser.ParseException, IOException, ApiException {

		Optional<User> opOldUser = this.userRepository.findByEmail(newUserDto.getEmail());
		if (opOldUser.isPresent()) {
			throw new ApiAlreadyExistException("Cette email est déjà utilisée");
		} else {
			User newUser = this.userRequestConverter.dtoToEntity(newUserDto, User.class);
			Collection<FileDb> files = getUserFileDbs(newUserDto, newUser, cv, coverLetter);
			if (files.size() > 0) {
				newUser.setFiles(files);
			}
			String encodedPassword = bCryptPasswordEncoder.encode(newUserDto.getPassword());
			newUser.setPassword(encodedPassword);
			newUser.setRoles(roles);

			User user = this.userRepository.save(newUser);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("firstName", user.getFirstName());
			params.put("lastName", user.getName());

			this.mailService.prepareMail(MailSubject.RegistrationConfirm, "Confirmation d'inscription", user.getEmail(),
					params, null);
		}
	}

	@Override
	public UserResponseDto updateUser(Long id, UserRequestDto userDto, MultipartFile cv, MultipartFile coverLetter)
			throws IOException, ApiException {

		User currentUser = this.userRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cet utilisateur n'existe pas !"));
		userDto.setId(id);
		User newUser = this.userRequestConverter.dtoToEntity(userDto, User.class);
		Collection<FileDb> files = getUserFileDbs(userDto, newUser, cv, coverLetter);
		// KEEP THE ROLES
		newUser.setRoles(currentUser.getRoles());

		// KEEP THE OLD FILES
		for (FileDb f : currentUser.getFiles()) {
			if (f.getType() == FileTypeEnum.CV) {
				boolean containsCV = false;
				for (FileDb sF : files) {
					if (sF.getType() == FileTypeEnum.CV) {
						containsCV = true;
					}
				}
				if (containsCV == false) {
					files.add(f);
				}
			}

			if (f.getType() == FileTypeEnum.COVER_LETTER) {
				boolean containsLetter = false;
				for (FileDb sF : files) {
					if (sF.getType() == FileTypeEnum.COVER_LETTER) {
						containsLetter = true;
					}
				}

				if (containsLetter == false) {
					files.add(f);
				}
			}
		}

		newUser.setFiles(files);

		newUser.setPassword(currentUser.getPassword());

		return this.userResponseConverter.entityToDto(this.userRepository.save(newUser), UserResponseDto.class);
	}

	@Override
	public User findByEmail(String email) throws ApiNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ApiNotFoundException("L'utilisateur n'existe pas"));
	}

	private Collection<FileDb> getUserFileDbs(UserRequestDto userDto, User user, MultipartFile cv,
			MultipartFile coverLetter) throws IOException, ApiException {
		Collection<FileDb> files = new ArrayList<>();

		if (cv != null) {
			if (!cv.getContentType().equals(ACCEPTED_FILE_FORMAT)) {
				throw new ApiException("Le CV doit respecter le format pdf", HttpStatus.BAD_REQUEST);
			}
			FileDb fileDb = new FileDb(null, cv.getOriginalFilename(), FileTypeEnum.CV, cv.getBytes(), user);
			files.add(fileDb);
		}
		if (coverLetter != null) {
			if (!coverLetter.getContentType().equals(ACCEPTED_FILE_FORMAT)) {
				throw new ApiException("La lettre de motivation doit respecter le format pdf", HttpStatus.BAD_REQUEST);
			}
			FileDb fileDb = new FileDb(null, coverLetter.getOriginalFilename(), FileTypeEnum.COVER_LETTER,
					coverLetter.getBytes(), user);
			files.add(fileDb);

		}
		return files;
	}

}
