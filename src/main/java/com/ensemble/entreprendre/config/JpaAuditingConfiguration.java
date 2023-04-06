package com.ensemble.entreprendre.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

	@Value("${connected.in.anonymous.value}")
	String connectedInAnonymousValue;

	@Bean
	public AuditorAware<String> auditorProvider() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String who = Optional.ofNullable(authentication != null ? authentication.getName() : null)
				.orElse(connectedInAnonymousValue);
		return () -> Optional.ofNullable(who);
	}
}
