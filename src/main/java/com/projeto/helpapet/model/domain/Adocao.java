package com.projeto.helpapet.model.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Adocao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", columnDefinition = "integer")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Column(name = "status", columnDefinition = "integer")
	private Integer status;

	@Column(name = "data_inicio", columnDefinition = "DATE")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataInicio;

	@Column(name = "data_termino", columnDefinition = "DATE")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataTermino;

	@NotNull
	@JoinColumn(name = "id_animal_fk", referencedColumnName = "id")
	@ManyToOne
	private Animal idAnimalFk;

	@NotNull
	@JoinColumn(name = "id_adotante_fk", referencedColumnName = "id_usuario")
	@ManyToOne
	private Adotante idAdotanteFk;

	public Adocao() {
	}

	public Adocao(Integer id,Integer status, Date dataInicio, Date dataTermino, Animal idAnimalFk,
			Adotante idAdotanteFk) {
		super();
		this.id = id;
		this.status = status;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.idAnimalFk = idAnimalFk;
		this.idAdotanteFk = idAdotanteFk;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Animal getIdAnimalFk() {
		return idAnimalFk;
	}

	public void setIdAnimalFk(Animal idAnimalFk) {
		this.idAnimalFk = idAnimalFk;
	}

	public Adotante getIdAdotanteFk() {
		return idAdotanteFk;
	}

	public void setIdAdotanteFk(Adotante idAdotanteFk) {
		this.idAdotanteFk = idAdotanteFk;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Adocao other = (Adocao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
