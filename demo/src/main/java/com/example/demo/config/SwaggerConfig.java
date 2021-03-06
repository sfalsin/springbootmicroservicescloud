package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().
				apis(RequestHandlerSelectors.basePackage("com.example"))
				.paths(PathSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error.*"))).build();
	}

	private Predicate<RequestHandler> apis() {
		return RequestHandlerSelectors.basePackage("com.example.demo.service");
	}

	Predicate<RequestHandler> excludeClass(final Class<?> clazz) {
		return new Predicate<RequestHandler>() {
			@Override
			public boolean apply(RequestHandler input) {
				return input.getHandlerMethod().getBeanType().equals(clazz);
			}
		};
	}

	private ApiInfo apiInfo() {

		ApiInfo apiInfo = new ApiInfoBuilder().title("API de Avaliação de Livros")
				.description("Essa é a API de Avaliação de Livros.").license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0").termsOfServiceUrl("/service.html")
				.version("1.0.0")
				.contact(new Contact("Marcel Ferry", "www.marcelferry.com.br", "marcelferry@marcelferry.com.br"))
				.build();

		return apiInfo;
	}
	

}
