package com.projeto.helpapet.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.projeto.helpapet.model.services.validation.NewSenha;
@NewSenha
public class SenhaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String senha;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String novaSenha;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String confirmarSenha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	

}