package com.projeto.helpapet.model.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.helpapet.model.domain.enums.Perfil;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_usuario", columnDefinition = "integer")
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer idUsuario;

	@NotNull
	@Column(name = "nome", columnDefinition = "varchar(100)")
	private String nome;

	@AssertTrue(message = "Tem que aceitar o termo de uso")
	@NotNull
	@Column(name = "termo", columnDefinition = "Boolean")
	private Boolean termo;

	@NotNull
	@Column(name = "email", unique = true, columnDefinition = "varchar(100)")
	private String email;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_atualizacao", columnDefinition = "DATE")
	private Date dataAtualizacao;

	@Column(name = "data_cadastro", columnDefinition = "DATE")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataCadastro;

	@NotNull
	@Column(name = "ativo", columnDefinition = "Boolean")
	private Boolean ativo;

	@JsonIgnore
	@NotNull
	@Column(name = "senha", columnDefinition = "varchar(255)")
	private String senha;

	@NotNull
	@Column(name = "municipio", columnDefinition = "varchar(100)")
	private String municipio;

	@NotNull
	@Column(name = "cep", columnDefinition = "varchar(9)")
	private String cep;

	@NotNull
	@Column(name = "uf", columnDefinition = "varchar(2)")
	private String uf;

	@NotNull
	@Column(name = "bairro", columnDefinition = "varchar(60)")
	private String bairro;

	@NotNull
	@Column(name = "logradouro", columnDefinition = "varchar(100)")
	private String logradouro;

	@NotNull
	@Column(name = "numero", columnDefinition = "varchar(5)")
	private String numero;

	@Column(name = "foto", columnDefinition = "varchar(150)")
	private String foto;

	@NotNull
	@Column(name = "telefone1", columnDefinition = "varchar(15)")
	private String telefone1;

	@Column(name = "telefone2", columnDefinition = "varchar(15)")
	private String telefone2;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="perfil")
	private Set<Integer> perfil = new HashSet<>();

	public Usuario() {

	}

	public Usuario(Integer idUsuario, String nome, String email, Date dataAtualizacao, Date dataCadastro, Boolean ativo,
			String senha, String municipio, String cep, String uf, String bairro, String logradouro, String numero,
			String foto, Boolean termo, String telefone1, String telefone2) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.dataAtualizacao = dataAtualizacao;
		this.dataCadastro = dataCadastro;
		this.ativo = ativo;
		this.senha = senha;
		this.municipio = municipio;
		this.cep = cep;
		this.uf = uf;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.foto = foto;
		this.termo = termo;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;

	}

	public void setPerfil(Set<Integer> perfil) {
		this.perfil = perfil;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public Boolean getTermo() {
		return termo;
	}

	public void setTermo(Boolean termo) {
		this.termo = termo;
	}
	
	public Set<Perfil> getPerfil() {
		return perfil.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		this.perfil.add(perfil.getCod());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
}
