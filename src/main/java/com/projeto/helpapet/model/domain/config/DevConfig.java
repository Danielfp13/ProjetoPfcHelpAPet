package com.projeto.helpapet.model.domain.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.projeto.helpapet.model.services.EmailService;
import com.projeto.helpapet.model.services.SmtpEmailService;

@Configuration

@Profile("dev")
public class DevConfig {

	@Value("${spring.jpa.hibernate.ddl-auto}")

	private String strategy;

	@Bean

	public boolean instantiateDatabase() throws ParseException {

		if (!"create".equals(strategy)) {

			return false;

		}

		return true;

	}

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
