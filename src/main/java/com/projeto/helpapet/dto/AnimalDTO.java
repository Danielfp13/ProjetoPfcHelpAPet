package com.projeto.helpapet.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.projeto.helpapet.model.domain.Animal;
import com.projeto.helpapet.model.domain.ArquivoAnimal;

public class AnimalDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String especie;
	private List<ArquivoAnimal> fotos = new ArrayList<ArquivoAnimal>();

	public AnimalDTO() {

	}

	public AnimalDTO(Animal obj) {
		id = obj.getId();
		nome = obj.getNome();
		especie = obj.getEspecie();
		fotos = obj.getArquivoAnimalList();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public List<ArquivoAnimal> getFotos() {
		return fotos;
	}

	public void setFotos(List<ArquivoAnimal> fotos) {
		this.fotos = fotos;
	}


	
}
