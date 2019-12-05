package com.projeto.helpapet.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.helpapet.dto.AdocaoDTO;
import com.projeto.helpapet.model.domain.Adocao;
import com.projeto.helpapet.model.domain.Animal;
import com.projeto.helpapet.model.services.AdocaoService;
import com.projeto.helpapet.model.services.AnimalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Adocao Endpoint")
@RestController
@RequestMapping(value = "/adocao")
public class AdocaoResource {

	@Autowired
	private AnimalService animalService;
	
	@Autowired
	private AdocaoService adocaoService;

	//AdocaoService adocaoService = new AdocaoService();
	// cadastro
	@ApiOperation(value = "adotar")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Animal objAnimal) {
		Adocao obj = animalService.fromAdotar(objAnimal);
		obj = animalService.insert(obj);
		// pega a URI do novo recurso que foi inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}	
	
	@ApiOperation(value = "Confirmar ou cancelar adoção")
	@PreAuthorize("hasAnyRole('INSTITUICAO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AdocaoDTO adocaoDto, @PathVariable Integer id) {
		Adocao adocao = adocaoService.update(adocaoDto,id);
		return ResponseEntity.noContent().build();
	}
	
	// busca por id
	@ApiOperation(value = "buscar animal por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Adocao> find(@PathVariable Integer id) {
		Adocao obj = adocaoService.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
}

