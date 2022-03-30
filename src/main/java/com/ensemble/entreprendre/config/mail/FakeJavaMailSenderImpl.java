package com.ensemble.entreprendre.config.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeJavaMailSenderImpl extends JavaMailSenderImpl {

	@Override
	public void send(SimpleMailMessage simpleMessage) {
		log.debug(simpleMessage.toString());
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) {
		for (SimpleMailMessage simpleMailMessage : simpleMessages) {
			log.debug(simpleMailMessage.toString());
		}
	}

	@Override
	public void send(MimeMessage mimeMessage) {
		log.debug(mimeMessage.toString());
	}

	@Override
	public void send(MimeMessage... mimeMessages) {
		for (MimeMessage simpleMailMessage : mimeMessages) {
			log.debug(simpleMailMessage.toString());
		}
	}

	@Override
	public void send(MimeMessagePreparator mimeMessagePreparator) {
		log.debug(mimeMessagePreparator.toString());
	}

	@Override
	public void send(MimeMessagePreparator... mimeMessagePreparators) {
		for (MimeMessagePreparator simpleMailMessage : mimeMessagePreparators) {
			log.debug(simpleMailMessage.toString());
		}
	}

}
