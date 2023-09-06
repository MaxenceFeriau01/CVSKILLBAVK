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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.ensemble.entreprendre.domain.User_;
import com.ensemble.entreprendre.domain.enumeration.FileTypeEnum;
import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.dto.UserRequestDto;
import com.ensemble.entreprendre.dto.UserResponseDto;
import com.ensemble.entreprendre.dto.UserStatDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.filter.BasicDtoFilter;
import com.ensemble.entreprendre.filter.IndividualAnalysisFilterService;
import com.ensemble.entreprendre.filter.IndividualAnalysisUserDtoFilter;
import com.ensemble.entreprendre.filter.UserDtoFilter;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.security.helper.JwtTokenUtilBean;
import com.ensemble.entreprendre.service.IMailService;
import com.ensemble.entreprendre.service.IUserService;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {

    public static final String ACCEPTED_FILE_FORMAT = "application/pdf";
    public static final String RESET_PASSWORD_PATH = "/reset-password";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    GenericConverter<User, UserResponseDto> userResponseConverter;

    @Autowired
    GenericConverter<User, UserStatDto> userStatConverter;

    @Autowired
    GenericConverter<User, AuthenticationResponseDto> authenticationResponseConverter;

    @Autowired
    GenericConverter<User, UserRequestDto> userRequestConverter;

    @Autowired
    IMailService mailService;

    @Autowired
    IndividualAnalysisFilterService individualAnalysisFilterService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtilBean jwtTokenUtilBean;

    @Value("${front.url}")
    String frontUrl;

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable, UserDtoFilter filter) {
        Specification<User> specification = null;

        specification = addCriterias(filter, specification);

        if (specification == null) {
            return this.userResponseConverter.entitiesToDtos(this.userRepository.findAll(pageable),
                    UserResponseDto.class);
        }

        return this.userResponseConverter.entitiesToDtos(this.userRepository.findAll(specification, pageable),
                UserResponseDto.class);
    }

    @Override
    public Page<UserResponseDto> getAllIndividualAnalysis(Pageable pageable, IndividualAnalysisUserDtoFilter filter){
        Specification<User> specification = null;

        specification = this.individualAnalysisFilterService.addCriterias(filter, specification);

        if (specification == null) {
            return this.userResponseConverter.entitiesToDtos(this.userRepository.findAll(pageable),
                    UserResponseDto.class);
        }
        return this.userResponseConverter.entitiesToDtos(this.userRepository.findAll(specification, pageable),
                UserResponseDto.class);
    }
      
    @Override
    public void delete(long id) throws ApiException {
        User toDelete = this.userRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cet utilisateur n'existe pas !"));
        this.userRepository.delete(toDelete);

    }

    @Override
    public UserResponseDto getById(long id) throws ApiNotFoundException {
        return this.userResponseConverter.entityToDto(this.userRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cet utilisateur n'existe pas !")), UserResponseDto.class);
    }

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
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Utilisateur inconnu"));
        if (user.isActivated() == false) {
            throw new AccessDeniedException("Vous ne pouvez pas vous connecter !");
        }
        return this.authenticationResponseConverter.entityToDto(user, AuthenticationResponseDto.class);
    }

    @Override
    public UserResponseDto findByEmailToUserResponseDto(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Utilisateur inconnu"));
        return this.userResponseConverter.entityToDto(user, UserResponseDto.class);
    }

    @Override
    public List<Long> getAppliedCompanies(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Utilisateur inconnu"));
        return user.getAppliedCompanies().stream().map(n -> n.getId()).collect(Collectors.toCollection(ArrayList::new));

    }

    @Override
    public void active(long id, boolean activated) throws ApiException {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cet utilisateur n'existe pas !"));
        user.setActivated(activated);
        this.userRepository.save(user);

    }

    @Override
    public void createUser(UserRequestDto newUserDto, Collection<Role> roles, MultipartFile cv,
            MultipartFile coverLetter) throws EntityNotFoundException, MessagingException, ParseException,
            org.apache.velocity.runtime.parser.ParseException, IOException, ApiException {

        Optional<User> opOldUser = this.userRepository.findByEmail(newUserDto.getEmail());
        if (opOldUser.isPresent()) {
            throw new ApiAlreadyExistException("Cet email est déjà utilisé");
        } else {
            User newUser = this.userRequestConverter.dtoToEntity(newUserDto, User.class);
            Collection<FileDb> files = getUserFileDbs(newUserDto, newUser, cv, coverLetter);
            if (files.size() > 0) {
                newUser.setFiles(files);
            }
            String encodedPassword = bCryptPasswordEncoder.encode(newUserDto.getPassword());
            newUser.setPassword(encodedPassword);
            newUser.setRoles(roles);
            newUser.setUpdateProfil(0);

            User user = this.userRepository.save(newUser);

            HashMap<String, Object> params = new HashMap<String, Object>();

            params.put("firstName", user.getFirstName());
            params.put("lastName", user.getName());

            this.mailService.prepareMail(MailSubject.RegistrationConfirm, "Confirmation d'inscription", user.getEmail(),
                    params, null);

            Collection<User> admins = userRepository.findByRoles_Role(RoleEnum.ROLE_ADMIN);
            params.put("user", user);
            for (User admin : admins) {
                params.put("admin", admin);
                mailService.prepareMail(MailSubject.AdminRegistrationConfirm, "Notification d'inscription",
                        admin.getEmail(), params, null);
            }
        }
    }

    public void updateResetPasswordToken(String email) throws ApiNotFoundException, EntityNotFoundException,
            MessagingException, org.apache.velocity.runtime.parser.ParseException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiNotFoundException("Cet utilisateur n'existe pas !"));
        String token = RandomString.make(30);
        user.setResetPasswordToken(token);
        userRepository.save(user);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getName());
        params.put("resetPasswordUrl", this.frontUrl + user.getResetPasswordToken() + RESET_PASSWORD_PATH);

        this.mailService.prepareMail(MailSubject.ResetPassword, "Réinitialisation du mot de passe", user.getEmail(),
                params, null);
    }

    public User getByResetPasswordToken(String token) throws ApiNotFoundException {
        return userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new ApiNotFoundException("Vous ne pouvez pas réinitialiser votre mot de passe !"));
    }

    public void updatePassword(Long userId, String newPassword) throws ApiNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiNotFoundException("Cet utilisateur n'existe pas !"));
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
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
        newUser.setActivated(currentUser.isActivated());

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

        Integer saveVisit = currentUser.getUpdateProfil();
        if(!currentUser.compareUserForUpdate(newUser)||
         !currentUser.filesAreEqual(currentUser.getFiles(),files)){
            saveVisit++;
        }
        

        newUser.setUpdateProfil(saveVisit);

        newUser.setFiles(files);

        newUser.setPassword(currentUser.getPassword());

        

        return this.userResponseConverter.entityToDto(this.userRepository.save(newUser), UserResponseDto.class);
    }

    @Override
    public User findByEmail(String email) throws ApiNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiNotFoundException("L'utilisateur n'existe pas"));
    }

    private Specification<User> addCriterias(UserDtoFilter filter, Specification<User> specification) {
        if (filter != null) {

            if (filter.getName() != null) {
                specification = addNameCriteria(filter, specification);
            }

        }
        return specification;
    }

    private Specification<User> addNameCriteria(UserDtoFilter filter, Specification<User> origin) {
        Specification<User> target = (root, criteriaQuery, criteriaBuilder) -> {
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.upper(root.get(User_.NAME)),
                        "%" + filter.getName().toUpperCase() + "%");
            } else {
                return criteriaBuilder.and();
            }
        };
        return ensureSpecification(origin, target);
    }

    private Specification<User> ensureSpecification(Specification<User> origin, Specification<User> target) {
        if (origin == null)
            return target;
        if (target == null)
            return origin;
        return Specification.where(origin).and(target);
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

    public AuthenticationResponseDto authenticate(String email, String password) throws ApiException {
        final UserDetails userDetails = this.loadUserByUsername(email);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                    password));
        } catch (BadCredentialsException e) {
            throw new ApiException("Identifiants incorrects", HttpStatus.NOT_FOUND);
        }
        AuthenticationResponseDto user = this.findByEmailToAuthenticationResponseDto(email);
        user.setToken(jwtTokenUtilBean.generateToken(userDetails));

        return user;
    }

    public Page<UserStatDto> getUserStats(Pageable pageable) {
        return this.userStatConverter.entitiesToDtos(this.userRepository.findAll(pageable),
                UserStatDto.class);
    };

}
