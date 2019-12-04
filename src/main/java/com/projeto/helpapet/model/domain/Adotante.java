package com.projeto.helpapet.model.domain;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.helpapet.model.domain.enums.Perfil;

@Entity
@PrimaryKeyJoinColumn(name="id_usuario")
public class Adotante extends Usuario {

	private static final long serialVersionUID = 1L;
	@NotNull
	@Column(name = "cpf", columnDefinition = "varchar(11)", unique = true)
	private String cpf;

	@NotNull
	@Column(name = "rg", columnDefinition = "varchar(11)", unique = true)
	private String rg;

	@NotNull
	@Column(name = "dataNascimento", columnDefinition = "DATE")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	
	@NotNull
	@Column(name = "sobrenome", columnDefinition = "varchar(50)")
	private String sobrenome;



	@JsonIgnore
	@OneToMany( mappedBy = "idAdotanteFk")
	private List<Adocao> adocaoList = new ArrayList<>();

	public Adotante() {
		addPerfil(Perfil.ADOTANTE);
	}

	public Adotante(Integer idUsuario, String nome, String email, Date dataAtualizacao, Date dataCadastro,
			Boolean ativo, String senha, String municipio, String cep, String uf, String bairro, String logradouro,
			String numero, String foto, String cpf, String rg, Date dataNascimento,Boolean termo, String telefone1, String telefone2,String sobrenome) {
		super(idUsuario, nome, email, dataAtualizacao, dataCadastro, ativo, senha, municipio, cep, uf, bairro, logradouro,
				numero, foto,termo, telefone1,telefone2);
		this.cpf = cpf;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
		this.sobrenome = sobrenome;
		addPerfil(Perfil.ADOTANTE);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Adocao> getAdocaoList() {
		return adocaoList;
	}

	public void setAdocaoList(List<Adocao> adocaoList) {
		this.adocaoList = adocaoList;
	}
	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	@Override
	public String toString() {
		//NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Bem vindo a Help a Pet seu cadastro foi realizado com sucesso.\n  ");
		builder.append("\n Nome: ");
		builder.append(getNome());
		builder.append("\n Data Cadastro: ");
		builder.append(sdf.format(getDataCadastro()));
		builder.append("\n Email: ");
		builder.append(getEmail());
		builder.append("\n Municipio: ");
		builder.append(getMunicipio());
		builder.append("\n CEP: ");
		builder.append(getCep());
		builder.append("\n UF: ");
		builder.append(getUf());
		builder.append("\n Bairro: ");
		builder.append(getBairro());
		builder.append("\n Numero: ");
		builder.append(getNumero());
		return builder.toString();
	}
}
