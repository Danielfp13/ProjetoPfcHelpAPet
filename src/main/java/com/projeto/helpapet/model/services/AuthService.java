package com.projeto.helpapet.model.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.helpapet.model.domain.Usuario;
import com.projeto.helpapet.model.repositories.UsuarioRepository;
import com.projeto.helpapet.model.services.exception.AuthorizationException;
import com.projeto.helpapet.model.services.exception.ObjectNotFoundException;
import com.projeto.helpapet.security.UserSS;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {

		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String newPass = newPassword();
		usuario.setSenha(pe.encode(newPass));

		usuarioRepository.save(usuario);
		emailService.sendNewPasswordEmail(usuario, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
	public void sendAlterPassword(String senha) {

		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Usuario usuario = usuarioRepository.getOne(user.getId());
		usuario.setSenha(pe.encode(senha));

		usuarioRepository.save(usuario);
		emailService.sendNewPasswordEmail(usuario,senha);
	}
}