package com.projeto.helpapet.model.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.helpapet.dto.AdocaoDTO;
import com.projeto.helpapet.model.domain.Adocao;
import com.projeto.helpapet.model.domain.Animal;
import com.projeto.helpapet.model.repositories.AdocaoRepository;
import com.projeto.helpapet.model.services.exception.ObjectNotFoundException;

@Service
public class AdocaoService {
	
	@Autowired
	public AdocaoRepository adocaoRepository;

	//inserir
		@Transactional
		public Adocao update(AdocaoDTO obj, Integer id) {
			Adocao adocao = adocaoRepository.getOne(id);
			adocao.setStatus(obj.getStatus());
			adocao.setDataTermino(new Date());
		    adocao = adocaoRepository.save(adocao);
			return adocao;
		}
		
		// buscar por id
		public Adocao find(Integer id) {
			Optional<Adocao> obj = adocaoRepository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Animal.class.getName()));
		}		
}
