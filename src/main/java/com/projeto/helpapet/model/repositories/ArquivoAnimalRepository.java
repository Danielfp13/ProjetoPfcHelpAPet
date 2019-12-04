package com.projeto.helpapet.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.helpapet.model.domain.ArquivoAnimal;


public interface ArquivoAnimalRepository extends JpaRepository<ArquivoAnimal,Integer>{


}