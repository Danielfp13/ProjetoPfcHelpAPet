package com.projeto.helpapet.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.helpapet.model.domain.Instituicao;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {

	@Transactional(readOnly = true)
	Instituicao findByEmail(String email);

	Instituicao findByCnpj(String cnpj);

	@Query(value = "SELECT i FROM Instituicao i WHERE i.situacaoCadastro ='Pendente' ")
	List<Instituicao> findAllSituacaoCadastro();

}