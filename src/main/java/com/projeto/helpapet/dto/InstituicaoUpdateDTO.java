package com.projeto.helpapet.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CNPJ;

import com.projeto.helpapet.model.services.validation.InstituicaoUpdate;

@InstituicaoUpdate
public class InstituicaoUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private String nome;
	@Email(message = "Email inválido")
	private String email;

	private Date dataAtualizacao;
	private Date dataCadastro;
	private Boolean ativo;
	private String municipio;
	private String cep;
	private String uf;
	private String bairro;
	private String logradouro;
	private String numero;
	private String foto;
	@CNPJ(message = "CNPJ incorreto")
	private String cnpj;
	private Integer situacaoCadastro;
	private String descricaoInstituicao;
	private String telefone1;
	private String telefone2;

	public InstituicaoUpdateDTO() {

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

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getDescricaoInstituicao() {
		return descricaoInstituicao;
	}

	public void setDescricaoInstituicao(String descricaoInstituicao) {
		this.descricaoInstituicao = descricaoInstituicao;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public Integer getSituacaoCadastro() {
		return situacaoCadastro;
	}

	public void setSituacaoCadastro(Integer situacaoCadastro) {
		this.situacaoCadastro = situacaoCadastro;
	}

}
