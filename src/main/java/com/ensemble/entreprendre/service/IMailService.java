package com.ensemble.entreprendre.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.apache.velocity.runtime.parser.ParseException;
import org.springframework.core.io.Resource;

import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.exception.ApiNotFoundException;

public interface IMailService {
	void prepareMail(MailSubject invoice, String title, String recipient, HashMap<String, String> params, Optional <Collection<Resource>> attachments) throws EntityNotFoundException, ApiNotFoundException, MessagingException, ParseException;	
}