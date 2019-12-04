package com.projeto.helpapet.dto;

import java.io.Serializable;
import java.util.Date;

import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.services.validation.AnimalUpdate;

@AnimalUpdate
public class AnimalUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Instituicao idInstituicaoFk;
	private String idMicrochip;
	private String nome;
	private Integer porte;
	private Integer genero;
	private Boolean vacinado;
	private Date anoNascimento;
	private String raca;
	private Boolean vermifugado;
	private String especie;
	private Date dataRecolhimento;
	private Boolean esterilizado;
	private String cor;
	private String descricao;

	public AnimalUpdateDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdMicrochip() {
		return idMicrochip;
	}

	public void setIdMicrochip(String idMicrochip) {
		this.idMicrochip = idMicrochip;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPorte() {
		return porte;
	}

	public void setPorte(Integer porte) {
		this.porte = porte;
	}

	public Integer getGenero() {
		return genero;
	}

	public void setGenero(Integer genero) {
		this.genero = genero;
	}

	public Date getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(Date anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public Date getDataRecolhimento() {
		return dataRecolhimento;
	}

	public void setDataRecolhimento(Date dataRecolhimento) {
		this.dataRecolhimento = dataRecolhimento;
	}

	public Boolean getVacinado() {
		return vacinado;
	}

	public void setVacinado(Boolean vacinado) {
		this.vacinado = vacinado;
	}

	public Boolean getVermifugado() {
		return vermifugado;
	}

	public void setVermifugado(Boolean vermifugado) {
		this.vermifugado = vermifugado;
	}

	public Boolean getEsterilizado() {
		return esterilizado;
	}

	public void setEsterilizado(Boolean esterilizado) {
		this.esterilizado = esterilizado;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instituicao getIdInstituicaoFk() {
		return idInstituicaoFk;
	}

	public void setIdInstituicaoFk(Instituicao idInstituicaoFk) {
		this.idInstituicaoFk = idInstituicaoFk;
	}
}
