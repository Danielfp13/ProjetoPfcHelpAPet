package com.projeto.helpapet.model.services;

import org.springframework.mail.SimpleMailMessage;

import com.projeto.helpapet.model.domain.Adotante;
import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.domain.Usuario;

public interface EmailService {

	
	void sendOrderConfirmationEmail(Adotante objAdotante);
	void sendOrderConfirmationEmail(Instituicao objInstituicao);
	
	void sendEmail(SimpleMailMessage msg);
	
	
	void sendNewPasswordEmail(Usuario usuario, String newPass);
	void sendReleaseRegister(Instituicao obj, Usuario usuario);
	void sendOrderConfirmation(Instituicao obj);
}