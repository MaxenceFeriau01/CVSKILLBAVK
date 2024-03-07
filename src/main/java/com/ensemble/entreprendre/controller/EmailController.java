package com.ensemble.entreprendre.controller;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.CompanyEmailDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IMailService;

@RequestMapping(path = "/api/email")
@RestController
public class EmailController {

    @Autowired
    private IMailService mailService;

    @PostMapping(path = "/company-contact")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmailCompanyToEEDK(@RequestBody CompanyEmailDto email) throws ApiException, EntityNotFoundException, MessagingException {
        mailService.sendEmailCompanyToEEDK(email);
    }
}
