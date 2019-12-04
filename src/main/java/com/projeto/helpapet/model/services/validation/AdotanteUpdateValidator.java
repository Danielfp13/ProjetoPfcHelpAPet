package com.projeto.helpapet.model.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.projeto.helpapet.dto.AdotanteUpdateDTO;
import com.projeto.helpapet.model.domain.Adotante;
import com.projeto.helpapet.model.domain.Usuario;
import com.projeto.helpapet.model.repositories.AdotanteRepository;
import com.projeto.helpapet.model.repositories.UsuarioRepository;
import com.projeto.helpapet.resources.execepton.FieldMessage;

public class AdotanteUpdateValidator implements ConstraintValidator<AdotanteUpdate, AdotanteUpdateDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UsuarioRepository repositoryUsuario;

	@Autowired
	private AdotanteRepository repositoryAdotante;

	@Override
	public void initialize(AdotanteUpdate ann) {
	}

	@Override
	public boolean isValid(AdotanteUpdateDTO objUpdateDto, ConstraintValidatorContext context) {
//pega o id da uri

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		List<FieldMessage> list = new ArrayList<>();

		Usuario usuario = repositoryUsuario.findByEmail(objUpdateDto.getEmail());
		if (usuario != null && !usuario.getIdUsuario().equals(uriId)) {
			list.add(new FieldMessage("Email", "Email já existente:"));
		}

		Adotante adotante = repositoryAdotante.findByCpf(objUpdateDto.getCpf());
		if (adotante != null && !adotante.getIdUsuario().equals(uriId)) {
			list.add(new FieldMessage("Cpf", "CPF já existente:"));
		}

		adotante = repositoryAdotante.findByRg(objUpdateDto.getRg());
		if (adotante != null && !adotante.getIdUsuario().equals(uriId)) {
			list.add(new FieldMessage("Rg", "Rg já existente:"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFielName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
