package com.projeto.helpapet.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.services.validation.AnimalInsert;

@AnimalInsert
public class AnimalNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Instituicao idInstituicaoFk;
	
	private String idMicrochip;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	
	@NotNull(message = "Preenchimento obrigatório")
	private Integer porte;
	
	@NotNull(message = "Preenchimento obrigatório")
	private Integer genero;
	
	
	private Boolean vacinado;
	
	
	private Date anoNascimento;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String raca;
	
	
	private Boolean vermifugado;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String especie;
	
	private Date dataRecolhimento;
	
	
	private Boolean esterilizado;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cor;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String descricao;

	public AnimalNewDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instituicao getIdInstituicaoFk() {
		return idInstituicaoFk;
	}

	public void setIdInstituicaoFk(Instituicao idInstituicaoFk) {
		this.idInstituicaoFk = idInstituicaoFk;
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

	public Boolean getEsterilizado() {
		return esterilizado;
	}

	public void setEsterilizado(Boolean esterilizado) {
		this.esterilizado = esterilizado;
	}
}
