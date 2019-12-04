package com.projeto.helpapet.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.helpapet.dto.EmailDTO;
import com.projeto.helpapet.dto.SenhaDTO;
import com.projeto.helpapet.model.services.AuthService;
import com.projeto.helpapet.model.services.UserService;
import com.projeto.helpapet.security.JWTUtil;
import com.projeto.helpapet.security.UserSS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "AUTH Endpoint")
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@ApiOperation(value = "Atualizar token")
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Recuperar senha de usuario")
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Alterar senha de usuario")
	@RequestMapping(value = "/alter_password", method = RequestMethod.POST)
	public ResponseEntity<Void> alterPassword(@Valid @RequestBody SenhaDTO objDto) {
		service.sendAlterPassword(objDto.getNovaSenha());
		return ResponseEntity.noContent().build();
	}
}