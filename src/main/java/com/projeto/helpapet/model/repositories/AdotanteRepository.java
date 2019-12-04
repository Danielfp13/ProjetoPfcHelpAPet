package com.projeto.helpapet.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.helpapet.model.domain.Adotante;

public interface AdotanteRepository extends JpaRepository<Adotante,Integer>{
	@Transactional(readOnly=true)
	Adotante findByEmail(String email);
	Adotante findByCpf(String cpf);
	Adotante findByRg(String rg);
	Adotante findByTelefone1(String Telefone1);
	Adotante findByTelefone2(String Telefone2);
	
	
}
