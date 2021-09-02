package com.ensemble.entreprendre;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EntreprendreEnsembleBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntreprendreEnsembleBackApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		/* insert here all conversions specifics adaptations */
		
		return  modelMapper;
	}
}
