package com.ensemble.entreprendre.features.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.exception.ApiException;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class AuthenticationHooks {
    @Autowired
    HttpCallClient httpCallClient;

    @Autowired
    UserAuthentication userAuthentication;

    /**
     * Tag to be placed in the features files to be connected as ADMIN
     * 
     * @throws ApiException
     */
    @Before("@admin")
    public void beforeAdminTag() throws ApiException {
        String jwtToken = userAuthentication.authenticate(RoleEnum.ROLE_ADMIN);
        this.httpCallClient.setJwtToken(jwtToken);
    }

    @After("@admin")
    public void afterAdminTag() {
        this.httpCallClient.setJwtToken("");
    }

    /**
     * Tag to be placed in the features files to be connected as User
     * 
     * @throws ApiException
     */
    @Before("@user")
    public void beforeUserTag() throws ApiException {
        String jwtToken = userAuthentication.authenticate(RoleEnum.ROLE_USER);
        this.httpCallClient.setJwtToken(jwtToken);
    }

    @After("@user")
    public void afterUserTag() {
        this.httpCallClient.setJwtToken("");
    }

    /**
     * Tag to be placed in the features files to be connected as Company
     * 
     * @throws ApiException
     */
    @Before("@company")
    public void beforeCompanyTag() throws ApiException {
        String jwtToken = userAuthentication.authenticate(RoleEnum.ROLE_COMPANY);
        this.httpCallClient.setJwtToken(jwtToken);
    }

    @After("@company")
    public void afterCompanyTag() {
        this.httpCallClient.setJwtToken("");
    }

}
