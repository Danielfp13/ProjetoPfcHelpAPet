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

import org.hibernate.annotations.Check;

import com.projeto.helpapet.model.domain.enums.Perfil;

@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
@Check(constraints = "situacao_cadastro IN(1,2,3)")
public class Instituicao extends Usuario {

	private static final long serialVersionUID = 1L;

	
	@Column(name = "cnpj", columnDefinition = "varchar(15)", unique = true)
	private String cnpj;

	@NotNull
	@Column(name = "situacao_cadastro", columnDefinition = "integer")
	private Integer situacaoCadastro;

	@Column(name = "descricao_instituicao", columnDefinition = "TEXT")
	private String descricaoInstituicao;

	//@JsonIgnore
	@OneToMany(mappedBy = "instituicao")
	private List<Animal> animalList = new ArrayList<>();

	public Instituicao() {
		this.addPerfil(Perfil.VISITANTE);
	}

	public Instituicao(Integer idUsuario, String nome, String email, Date dataAtualizacao, Date dataCadastro,
			Boolean ativo, String senha, String municipio, String cep, String uf, String bairro, String logradouro,
			String numero, String foto, String cnpj, Integer situacaoCadastro, String descricaoInstituicao,
			Boolean termo, String telefone1, String telefone2) {
		super(idUsuario, nome, email, dataAtualizacao, dataCadastro, ativo, senha, municipio, cep, uf, bairro,
				logradouro, numero, foto, termo, telefone1, telefone2);
		this.cnpj = cnpj;
		this.situacaoCadastro = situacaoCadastro;
		this.descricaoInstituicao = descricaoInstituicao;
		this.addPerfil(Perfil.VISITANTE);
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Integer getSituacaoCadastro() {
		return situacaoCadastro;
	}

	public void setSituacaoCadastro(Integer situacaoCadastro) {
		this.situacaoCadastro = situacaoCadastro;
	}

	public String getDescricaoInstituicao() {
		return descricaoInstituicao;
	}

	public void setDescricaoInstituicao(String descricaoInstituicao) {
		this.descricaoInstituicao = descricaoInstituicao;
	}

	public List<Animal> getAnimalList() {
		return animalList;
	}

	public void setAnimalList(List<Animal> animalList) {
		this.animalList = animalList;
	}
	
	@Override
	public String toString() {
		//NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		StringBuilder builder = new StringBuilder();
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
