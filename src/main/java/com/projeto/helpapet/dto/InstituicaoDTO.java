package com.projeto.helpapet.dto;

import java.io.Serializable;

import com.projeto.helpapet.model.domain.Instituicao;

public class InstituicaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private String nome;
	private String email;
	private String foto;

	public InstituicaoDTO() {

	}

	public InstituicaoDTO(Instituicao obj) {
		idUsuario = obj.getIdUsuario();
		nome = obj.getNome();
		email = obj.getEmail();
		foto = obj.getFoto();
		
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
