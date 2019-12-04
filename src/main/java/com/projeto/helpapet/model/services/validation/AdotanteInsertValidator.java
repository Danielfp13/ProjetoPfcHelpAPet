package com.projeto.helpapet.model.services.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.projeto.helpapet.dto.AdotanteNewDTO;
import com.projeto.helpapet.model.domain.Adotante;
import com.projeto.helpapet.model.domain.Usuario;
import com.projeto.helpapet.model.repositories.AdotanteRepository;
import com.projeto.helpapet.model.repositories.UsuarioRepository;
import com.projeto.helpapet.resources.execepton.FieldMessage;

public class AdotanteInsertValidator implements ConstraintValidator<AdotanteInsert, AdotanteNewDTO> {

	@Autowired
	private UsuarioRepository repositoryUsuario;
	
	@Autowired
	private AdotanteRepository repositoryAdotante;

	@Override
	public void initialize(AdotanteInsert ann) {
	}

	@Override
	public boolean isValid(AdotanteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Usuario usuario  = repositoryUsuario.findByEmail(objDto.getEmail());
	

		if (usuario != null ) {
			list.add(new FieldMessage("Email", "Email ja existente:"));
		}
		
		LocalDate aux = objDto.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int idade = Period.between(aux, LocalDate.now()).getYears();

		if (idade < 18) {

			list.add(new FieldMessage("dataNascimento", "Tem que ser maior de idade:"));
		}
		
		Adotante adotante = repositoryAdotante.findByCpf(objDto.getCpf());
		if (adotante != null) {
			list.add(new FieldMessage("Cpf", "CPF ja existente:"));
		}
		
		
		adotante = repositoryAdotante.findByRg(objDto.getRg());
		if (adotante != null) {
			list.add(new FieldMessage("Rg", "Rg ja existente:"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFielName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
