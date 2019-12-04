package com.projeto.helpapet.model.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.projeto.helpapet.dto.AnimalNewDTO;
import com.projeto.helpapet.model.domain.Animal;
import com.projeto.helpapet.model.repositories.AnimalRepository;
import com.projeto.helpapet.resources.execepton.FieldMessage;

public class AnimalInsertValidator implements ConstraintValidator<AnimalInsert, AnimalNewDTO> {

	@Autowired
	private AnimalRepository repositoryAnimal;

	@Override
	public void initialize(AnimalInsert ann) {
	}

	@Override
	public boolean isValid(AnimalNewDTO objNewDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objNewDto.getIdMicrochip() != null) {
			Animal aux = repositoryAnimal.findByIdMicrochip(objNewDto.getIdMicrochip());
			if (aux != null) {
				list.add(new FieldMessage("idMicrochip", "IdMicrochip ja existente:"));
			}
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFielName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}