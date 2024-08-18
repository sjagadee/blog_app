package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot - Blog App REST APIs",
				description = "Documentation for Blog App REST APIs (Spring Boot)",
				version = "v1.0",
				contact = @Contact(
						name = "Srinivas",
						email = "111srinivas@gmail.com",
						url = "https://github.com/sjagadee"
				),
				license = @License(
						name = "Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot - Blog App Docs",
				url = "https://github.com/sjagadee/blog_app"
		)
)
public class BlogRestapiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogRestapiApplication.class, args);
	}

}
