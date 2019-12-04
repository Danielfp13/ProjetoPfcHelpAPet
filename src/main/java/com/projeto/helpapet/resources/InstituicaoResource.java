package com.projeto.helpapet.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.helpapet.dto.InstituicaoDTO;
import com.projeto.helpapet.dto.InstituicaoNewDTO;
import com.projeto.helpapet.dto.InstituicaoUpdateDTO;
import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.services.InstituicaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Instituição Endpoint")
@RestController
@RequestMapping(value = "/instituicoes")
public class InstituicaoResource {

	@Autowired
	private InstituicaoService service;

	// cadastro
	@ApiOperation(value = "Cadastro Instituição")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody InstituicaoNewDTO objNewDto) {
		Instituicao obj = service.fromDTO(objNewDto);
		obj = service.insert(obj);
		// pega a URI do novo recurso que foi inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdUsuario())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	// busca por id
	@ApiOperation(value = "Busca de instituição por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Instituicao> find(@PathVariable Integer id) {
		Instituicao obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	// alterar
	@ApiOperation(value = "Alterar cadastro de instituição")
	@PreAuthorize("hasAnyRole('INSTITUICAO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody InstituicaoUpdateDTO objUpdateDto,
			@PathVariable Integer id) {
		Instituicao obj = service.fromDTO(objUpdateDto);
		obj.setIdUsuario(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	// excluir
	@ApiOperation(value = "Excluir instituição")
	@PreAuthorize("hasAnyRole('INSTITUICAO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

//lista todos os usuarios
	@ApiOperation(value = "Listar todas as instituições")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<InstituicaoDTO>> findAll() {
		List<Instituicao> lista = service.findAll();
		List<InstituicaoDTO> listaDto = lista.stream().map(obj -> new InstituicaoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);
	}

	// paginação
	@ApiOperation(value = "Listar todas as instituições com paginação")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<InstituicaoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Instituicao> lista = service.findPage(page, linesPerPage, orderBy, direction);
		Page<InstituicaoDTO> listaDto = lista.map(obj -> new InstituicaoDTO(obj));
		return ResponseEntity.ok().body(listaDto);
	}

	// inserir imagem
	@ApiOperation(value = "Inseriri imagem de instituição")
	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}

	// Listar instituiçoes pendentes
	@ApiOperation(value = "Listar todas as instituições pendentes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/peding", method = RequestMethod.GET)
	public ResponseEntity<List<InstituicaoDTO>> findPending() {
		List<Instituicao> lista = service.findPending();
		List<InstituicaoDTO> listaDto = lista.stream().map(obj -> new InstituicaoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);
	}
	

	// Liberar
	@ApiOperation(value = "Liberar instituições pendentes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/release/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> releaseAccess(@PathVariable Integer id) {
		service.releaseAccess(id);
		return ResponseEntity.noContent().build();
	}
}
