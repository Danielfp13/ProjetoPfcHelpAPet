package com.projeto.helpapet.model.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.projeto.helpapet.model.services.EmailService;
import com.projeto.helpapet.model.services.SmtpEmailService;

@Configuration
@Profile("test")
public class TestConfig {


	
//	@Bean
//	public EmailService emailService() {
//		return new MockEmailService();
//	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}