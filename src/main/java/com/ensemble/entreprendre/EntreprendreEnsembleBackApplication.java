package com.ensemble.entreprendre;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EntreprendreEnsembleBackApplication {

	@Value("${allowed.origins}")
	private String allowedOrigins;

	public static void main(String[] args) {
		SpringApplication.run(EntreprendreEnsembleBackApplication.class, args);
	}

	/**
	 * Enables CORS requests from any origin to any endpoint in the application
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins(allowedOrigins);

			}
		};
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setCollectionsMergeEnabled(true);
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		modelMapper.getConfiguration().setPreferNestedProperties(false);

		/* insert here all conversions specifics adaptations */

		return modelMapper;
	}
}
