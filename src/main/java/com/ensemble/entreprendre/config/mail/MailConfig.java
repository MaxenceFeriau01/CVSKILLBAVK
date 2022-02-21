package com.ensemble.entreprendre.config.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.StringUtils;

@Configuration
@PropertySource("classpath:mail/mail.properties")
public class MailConfig {

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.protocol:smtp}")
	private String protocol;

	@Value("${spring.mail.enable:false}")
	private boolean enable;

	@Value("${spring.mail.properties.mail.smtp.auth:false}")
	private boolean auth;

	@Value("${spring.mail.properties.mail.smtp.connectiontimeout:5000}")
	private Long connectiontimeout;

	@Value("${spring.mail.properties.mail.smtp.timeout:5000}")
	private Long timeout;

	@Value("${spring.mail.properties.mail.smtp.writetimeout:5000}")
	private Long writetimeout;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable:false}")
	private boolean tlsenable;

	@Value("${spring.mail.properties.mail.debug:false}")
	private boolean debug;

	@Bean
	public JavaMailSender javaMailSender() {
		if (!enable) {
			return new FakeJavaMailSenderImpl();
		}
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		if (StringUtils.hasText(protocol)) {
			mailSender.setProtocol(protocol);
		}
		if (StringUtils.hasText(username)) {
			mailSender.setUsername(username);
		}
		if (StringUtils.hasText(password)) {
			mailSender.setPassword(password);
		}
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", auth);
		mailProperties.put("mail.smtp.connectiontimeout", connectiontimeout);
		mailProperties.put("mail.smtp.timeout", timeout);
		mailProperties.put("mail.smtp.writetimeout", writetimeout);
		mailProperties.put("mail.smtp.starttls.enable", tlsenable);
		mailProperties.put("mail.debug", debug);
		mailSender.setJavaMailProperties(mailProperties);

		return mailSender;
	}

}