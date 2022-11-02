package com.ensemble.entreprendre.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.ensemble.entreprendre.exception.ApiException;

public interface IAuthenticationService {

    UserDetails getConnectedUser() throws ApiException;
  
}