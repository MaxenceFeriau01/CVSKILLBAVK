package com.ensemble.entreprendre.features.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.ensemble.entreprendre.EntreprendreEnsembleBackApplication;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = EntreprendreEnsembleBackApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {

}
