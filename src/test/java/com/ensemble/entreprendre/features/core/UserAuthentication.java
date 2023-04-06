package com.ensemble.entreprendre.features.core;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.dto.AuthenticationResponseDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.repository.IRoleRepository;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.service.IUserService;

@Service
public class UserAuthentication {

    private static final String PASSWORD = "modis";

    @Autowired
    IUserService userService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String getBearerToken(RoleEnum roleToCreate) throws ApiException {
        AuthenticationResponseDto user = this.userService.authenticate("modis" + roleToCreate + "@modis.com", PASSWORD);
        return user.getToken();
    }

    private void createUser(RoleEnum roleToCreate) {
        User newUser = new User(null, "modis" + roleToCreate + "@modis.com", "modis", "modis",
                bCryptPasswordEncoder.encode(PASSWORD), "0161616161", 7500, LocalDate.now(), LocalDate.now(),
                LocalDate.now(), null,
                null, "M.", "BAC+5", "6month", true, null, null,
                Arrays.asList(this.roleRepository.findByRole(roleToCreate)), null);

        this.userRepository.save(newUser);
    }

    public String authenticate(RoleEnum roleToCreate) throws ApiException {
        this.createUser(roleToCreate);
        return getBearerToken(roleToCreate);
    }
}