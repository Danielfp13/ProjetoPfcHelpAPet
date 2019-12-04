package com.projeto.helpapet.model.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.projeto.helpapet.dto.AnimalUpdateDTO;
import com.projeto.helpapet.model.domain.Animal;
import com.projeto.helpapet.model.repositories.AnimalRepository;
import com.projeto.helpapet.resources.execepton.FieldMessage;

public class AnimalUpdateValidator implements ConstraintValidator<AnimalUpdate, AnimalUpdateDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AnimalRepository repositoryAnimal;

	@Override
	public void initialize(AnimalUpdate ann) {
	}

	@Override
	public boolean isValid(AnimalUpdateDTO objUpdateDto, ConstraintValidatorContext context) {
//pega o id da uri

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		List<FieldMessage> list = new ArrayList<>();

		if (objUpdateDto.getIdMicrochip() != null) {
			Animal animal = repositoryAnimal.findByIdMicrochip(objUpdateDto.getIdMicrochip());
			if (animal != null && !animal.getId().equals(uriId)) {
				list.add(new FieldMessage("idMiccrochip", "id microchip já existente:"));
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
