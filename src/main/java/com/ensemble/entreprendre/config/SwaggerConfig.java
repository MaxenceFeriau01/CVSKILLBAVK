package com.ensemble.entreprendre.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger.context.path:/}")
	private String context;

	 @Bean
	public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).pathMapping(this.context)
	/* .securitySchemes(Collections.singletonList(this.apiKey()))
	.securityContexts(Collections.singletonList(this.securityContext())) */
	.select()
	.apis(RequestHandlerSelectors.basePackage("com.ensemble.entreprendre")).paths(PathSelectors.any()).build();
	}

	 private ApiKey apiKey() {
		 return new ApiKey("apiKey", "Authorization", "header");
	}

	 private SecurityContext securityContext() {
		 return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	 private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
	}
}
