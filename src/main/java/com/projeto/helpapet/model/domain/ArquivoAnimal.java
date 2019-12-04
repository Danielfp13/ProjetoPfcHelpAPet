package com.projeto.helpapet.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ArquivoAnimal implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", columnDefinition = "integer")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@JsonIgnore
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_animal_fk", referencedColumnName = "id")
	@JsonIgnore
	private Animal animal;

	@Column(name = "foto", columnDefinition = "varchar(150)")
	private String foto;

	public ArquivoAnimal() {

	}

	public ArquivoAnimal(Integer id, Animal animal, String foto) {
		super();
		this.id = id;
		this.animal = animal;
		this.foto = foto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		ArquivoAnimal other = (ArquivoAnimal) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getFoto();
	}
	
	

	
}
