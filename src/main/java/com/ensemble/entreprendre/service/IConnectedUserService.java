package com.ensemble.entreprendre.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.ensemble.entreprendre.exception.ApiException;

public interface IConnectedUserService {

    UserDetails getConnectedUser() throws ApiException;

    String[] getConnectedUserRoles() throws ApiException;

    Boolean isAdmin() throws ApiException;

    Boolean isUserConnected();

}