package com.ensemble.entreprendre.domain;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.core.io.Resource;

import com.ensemble.entreprendre.domain.enumeration.MailSubject;

import lombok.Data;

@Data
public class Mail {

	private String title;

	private MailSubject subject;

	private String recipient;

	private HashMap<String, String> params;

	private Collection<Resource> attachments;

}