package com.projeto.helpapet.model.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.projeto.helpapet.dto.InstituicaoUpdateDTO;
import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.domain.Usuario;
import com.projeto.helpapet.model.repositories.InstituicaoRepository;
import com.projeto.helpapet.model.repositories.UsuarioRepository;
import com.projeto.helpapet.resources.execepton.FieldMessage;

public class InstituicaoUpdateValidator implements ConstraintValidator<InstituicaoUpdate, InstituicaoUpdateDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UsuarioRepository repositoryUsuario;

	@Autowired
	private InstituicaoRepository repositoryInstituicao;

	@Override
	public void initialize(InstituicaoUpdate ann) {
	}

	@Override
	public boolean isValid(InstituicaoUpdateDTO objUpdateDto, ConstraintValidatorContext context) {
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

		if (objUpdateDto.getCnpj() != null) {
			Instituicao instituicao = repositoryInstituicao.findByCnpj(objUpdateDto.getCnpj());
			if (instituicao != null && !instituicao.getIdUsuario().equals(uriId)) {
				list.add(new FieldMessage("Cnpj", "CNPJ já existente:"));
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
