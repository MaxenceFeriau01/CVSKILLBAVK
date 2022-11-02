package com.ensemble.entreprendre.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IAuthenticationService;

@Service

public class AuthenticationService implements IAuthenticationService {

    @Override
    public UserDetails getConnectedUser() throws ApiException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new ApiException("Utilisateur non connecté", HttpStatus.UNAUTHORIZED);
        }
        Object principal = authentication.getPrincipal();

        return (UserDetails) principal;

    }
}
