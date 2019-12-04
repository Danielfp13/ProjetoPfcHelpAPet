package com.projeto.helpapet.model.services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.projeto.helpapet.model.domain.Adotante;
import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.domain.Usuario;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Adotante obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromUsuario(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromUsuario(Adotante obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		//pra quem destinatario
		sm.setTo(obj.getEmail());
		//remetente
		sm.setFrom(sender);
		//assunto
		sm.setSubject("Cadastrado realizado com sucesso:");
		//data com horario do servidor
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//corpo do email
		sm.setText(obj.toString());
		return sm;
	}
	
	@Override
	public void sendOrderConfirmationEmail(Instituicao obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromUsuario(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromUsuario(Instituicao obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		//pra quem destinatario
		sm.setTo(obj.getEmail());
		//remetente
		sm.setFrom(sender);
		//assunto
		sm.setSubject("Cadastrado realizado e aguardando liberação do administrador:");
		//data com horario do servidor
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//corpo do email
		sm.setText("Bem vindo a Help a Pet seu cadastro foi realizado, e será avaliado pelo adm do sistema assim que for liberado você recerá um email.\n " + obj.toString());
		return sm;
	}
	
	@Override
	public void sendReleaseRegister(Instituicao obj, Usuario usuario) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromAdmin(obj,usuario);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareSimpleMailMessageFromAdmin(Instituicao obj,Usuario usuario) {
		SimpleMailMessage sm = new SimpleMailMessage();
		//pra quem destinatario
		sm.setTo(usuario.getEmail());
		//remetente
		sm.setFrom(sender);
		//assunto
		sm.setSubject("Um novo cadastro de instituiçao foi realizado:");
		//data com horario do servidor
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//corpo do email
		sm.setText("Olá um novo cadastro de instituição foi realizado.\n " + obj.toString());
		return sm;
	}
	
	@Override
	public void sendNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(usuario, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();

		sm.setTo(usuario.getEmail());

		sm.setFrom(sender);
		sm.setSubject("Mudança de senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Olá " + usuario.getNome()+ " sua nova senha é: " + newPass);
		return sm;
	}
	
	@Override
	public void sendOrderConfirmation(Instituicao obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromConfirmation(obj);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareSimpleMailMessageFromConfirmation(Instituicao obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		//pra quem destinatario
		sm.setTo(obj.getEmail());
		//remetente
		sm.setFrom(sender);
		//assunto
		sm.setSubject("Cadastrado liberado:");
		//data com horario do servidor
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//corpo do email
		sm.setText("Parabens " + obj.getNome() + " seu cadastro na Help a Pet foi liberado.\n " + obj.toString());
		return sm;
	}
	
	
	
}
