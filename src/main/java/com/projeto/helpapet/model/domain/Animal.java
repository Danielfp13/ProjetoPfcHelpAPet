package com.projeto.helpapet.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Animal implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", columnDefinition = "integer")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "id_microchip", columnDefinition = "varchar(50)", unique = true)
	private String idMicrochip;

	@NotNull
	@Column(name = "nome", columnDefinition = "varchar(100)")
	private String nome;

	@NotNull
	@Column(name = "porte", columnDefinition = "integer")
	private Integer porte;

	@NotNull
	@Column(name = "genero", columnDefinition = "integer")
	private Integer genero;

	@NotNull
	@Column(name = "vacinado", columnDefinition = "Boolean")
	private Boolean vacinado;

	@Column(name = "ano_nascimento", columnDefinition = "DATE")
	private Date anoNascimento;

	@NotNull
	@Column(name = "raca", columnDefinition = "varchar(30)")
	private String raca;

	@NotNull
	@Column(name = "vermifugado", columnDefinition = "Boolean")
	private Boolean vermifugado;

	@NotNull
	@Column(name = "especie", columnDefinition = "varchar(30)")
	private String especie;

	@NotNull
	@Column(name = "data_recolhimento", columnDefinition = "DATE")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataRecolhimento;

	@NotNull
	@Column(name = "esterilizado", columnDefinition = "Boolean")
	private Boolean esterilizado;

	@NotNull
	@Column(name = "cor", columnDefinition = "varchar(30)")
	private String cor;

	@Column(name = "descricao", columnDefinition = "text")
	private String descricao;

	@OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
	private List<ArquivoAnimal> arquivoAnimalList = new ArrayList<ArquivoAnimal>();

	
	@OneToMany(mappedBy = "idAnimalFk")
	@JsonIgnore
	private List<Adocao> adocaoList = new ArrayList<Adocao>();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_instituicao_fk", referencedColumnName = "id_usuario")
	private Instituicao instituicao;
	
	public Animal() {
		
	}

	public Animal(Integer id, String idMicrochip, String nome, Integer porte, Integer genero, Boolean vacinado,
			Date anoNascimento, String raca, Boolean vermifugado, String especie, Date dataRecolhimento,
			Boolean esterilizado, String cor, String descricao, Instituicao instituicao) {
		super();
		this.id = id;
		this.idMicrochip = idMicrochip;
		this.nome = nome;
		this.porte = porte;
		this.genero = genero;
		this.vacinado = vacinado;
		this.anoNascimento = anoNascimento;
		this.raca = raca;
		this.vermifugado = vermifugado;
		this.especie = especie;
		this.dataRecolhimento = dataRecolhimento;
		this.esterilizado = esterilizado;
		this.cor = cor;
		this.descricao = descricao;
		this.instituicao = instituicao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
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

	public List<ArquivoAnimal> getArquivoAnimalList() {
		return arquivoAnimalList;
	}

	public void setArquivoAnimalList(ArquivoAnimal animal) {
		this.arquivoAnimalList.add(animal);
	}

	public List<Adocao> getAdocaoList() {
		return adocaoList;
	}

	public void setAdocaoList(List<Adocao> adocaoList) {
		this.adocaoList = adocaoList;
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
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
