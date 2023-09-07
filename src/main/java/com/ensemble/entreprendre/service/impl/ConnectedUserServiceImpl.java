package com.ensemble.entreprendre.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IConnectedUserService;

@Service

public class ConnectedUserServiceImpl implements IConnectedUserService {

    @Value("${connected.in.anonymous.value}")
    String connectedInAnonymousValue;

    @Override
    public UserDetails getConnectedUser() throws ApiException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal.equals(connectedInAnonymousValue)) {
            throw new ApiException("Utilisateur non connecté", HttpStatus.UNAUTHORIZED);
        }

        return (UserDetails) principal;

    }

    @Override
    public String[] getConnectedUserRoles() throws ApiException {
        return this.getConnectedUser().getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

    }

    @Override
    public Boolean isUserConnected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals(connectedInAnonymousValue)) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean isAdmin() throws ApiException {
        return Arrays.stream(this.getConnectedUserRoles()).anyMatch(RoleEnum.ROLE_ADMIN.toString()::equals);
    }

    @Override
    public void isOwner(User owner) throws ApiException {
        UserDetails userDetails = this.getConnectedUser();

        if (!owner.getEmail().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("Accès interdit");
        }
    }
}
