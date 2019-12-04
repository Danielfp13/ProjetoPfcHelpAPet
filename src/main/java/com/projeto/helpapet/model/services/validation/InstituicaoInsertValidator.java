package com.projeto.helpapet.model.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.projeto.helpapet.dto.InstituicaoNewDTO;
import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.domain.Usuario;
import com.projeto.helpapet.model.repositories.InstituicaoRepository;
import com.projeto.helpapet.model.repositories.UsuarioRepository;
import com.projeto.helpapet.resources.execepton.FieldMessage;

public class InstituicaoInsertValidator implements ConstraintValidator<InstituicaoInsert, InstituicaoNewDTO> {

	@Autowired
	private UsuarioRepository repositoryUsuario;

	@Autowired
	private InstituicaoRepository repositoryInstituicao;

	@Override
	public void initialize(InstituicaoInsert ann) {
	}

	@Override
	public boolean isValid(InstituicaoNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Usuario usuario = repositoryUsuario.findByEmail(objDto.getEmail());

		if (usuario != null) {
			list.add(new FieldMessage("Email", "Email ja existente:"));
		}

		if (objDto.getCnpj() != null) {

			Instituicao instituicao = repositoryInstituicao.findByCnpj(objDto.getCnpj());
			if (instituicao != null) {
				list.add(new FieldMessage("Cnpj", "CNPJ ja existente:"));
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
