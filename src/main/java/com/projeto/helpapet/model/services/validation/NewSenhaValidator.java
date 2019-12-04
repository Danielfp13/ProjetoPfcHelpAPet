package com.projeto.helpapet.model.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.projeto.helpapet.dto.SenhaDTO;
import com.projeto.helpapet.model.services.UserService;
import com.projeto.helpapet.model.services.exception.AuthorizationException;
import com.projeto.helpapet.resources.execepton.FieldMessage;
import com.projeto.helpapet.security.UserSS;

public class NewSenhaValidator implements ConstraintValidator<NewSenha, SenhaDTO> {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Override
	public void initialize(NewSenha ann) {
	}

	@Override
	public boolean isValid(SenhaDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		if (!pe.matches(objDto.getSenha(),user.getPassword())) {
			list.add(new FieldMessage("senha", "Senha incorreta:"));
		}
		if (!objDto.getNovaSenha().equals(objDto.getConfirmarSenha())) {
			list.add(new FieldMessage("senha", "as senhas dever ser iguais:"));
		}

	
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFielName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
