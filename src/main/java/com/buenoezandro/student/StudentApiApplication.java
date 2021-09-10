package com.buenoezandro.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.buenoezandro.student.controller", "com.buenoezandro.student.service",
		"com.buenoezandro.student.config", "com.buenoezandro.student.api.exceptionhandler" })
@EntityScan(basePackages = "com.buenoezandro.student.model")
@EnableJpaRepositories(basePackages = "com.buenoezandro.student.repository")
public class StudentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApiApplication.class, args);
	}

}