package com.projeto.helpapet.resources;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.projeto.helpapet.dto.AnimalDTO;
import com.projeto.helpapet.dto.AnimalNewDTO;
import com.projeto.helpapet.dto.AnimalUpdateDTO;
import com.projeto.helpapet.model.domain.Animal;
import com.projeto.helpapet.model.services.AnimalService;
import com.projeto.helpapet.model.services.S3Service;
import com.projeto.helpapet.resources.utils.Upola;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Animal Endpoint")
@RestController
@RequestMapping(value = "/animais")
public class AnimalResource {

	@Autowired
	private S3Service s3Service;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Autowired
	private AnimalService service;

	// cadastro
	@ApiOperation(value = "Cadastro animal")
	@PreAuthorize("hasAnyRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AnimalNewDTO objNewDto) {
		Animal obj = service.fromDTO(objNewDto);
		obj = service.insert(obj);
		// pega a URI do novo recurso que foi inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// busca por id
	@ApiOperation(value = "buscar animal por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Animal> find(@PathVariable Integer id) {
		Animal obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	// alterar
	@ApiOperation(value = "Alterar cadastro animal")
	@PreAuthorize("hasAnyRole('INSTITUICAO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AnimalUpdateDTO objUpdateDto, @PathVariable Integer id) {
		Animal obj = service.fromDTO(objUpdateDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	// excluir
	@ApiOperation(value = "Excluir animal")
	@PreAuthorize("hasAnyRole('INSTITUICAO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

//lista todos os animais
	@ApiOperation(value = "Listar todos os animais")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AnimalDTO>> findAll() {
		List<Animal> lista = service.findAll();
		List<AnimalDTO> listaDto = lista.stream().map(obj -> new AnimalDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);
	}
	
	// listar todos os animais com paginação
	@ApiOperation(value = "Listar todos os animais com paginação")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<AnimalDTO>> fingPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Animal> lista = service.findPage(page, linesPerPage, orderBy, direction);
		Page<AnimalDTO> listaDto = lista.map(obj -> new AnimalDTO(obj));
		return ResponseEntity.ok().body(listaDto);

	}
	
	
	// listar todos os animais disponiveis para adocao com paginação
	@ApiOperation(value = "Listar animais disponiveis para adocao")
	@RequestMapping(value = "/disponiveis", method = RequestMethod.GET)
	public ResponseEntity<Page<AnimalDTO>> findAllAvailable(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Animal> lista = service.findAllAvailable(page, linesPerPage, orderBy, direction);
		Page<AnimalDTO> listaDto = lista.map(obj -> new AnimalDTO(obj));
		return ResponseEntity.ok().body(listaDto);

	}
	

// meus animais page
	@ApiOperation(value = "Listar meus animais com paginação")
	@RequestMapping(value = "/myanimals/page", method = RequestMethod.GET)
	public ResponseEntity<Page<AnimalDTO>> myanimals(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Animal> lista = service.myAnimalsPage(page, linesPerPage, orderBy, direction);
		Page<AnimalDTO> listaDto = lista.map(obj -> new AnimalDTO(obj));
		return ResponseEntity.ok().body(listaDto);
	}

	// meus animais
	@ApiOperation(value = "Listar meus animais")
	@RequestMapping(value = "/myanimals", method = RequestMethod.GET)
	public ResponseEntity<List<AnimalDTO>> myAnimals() {
		List<Animal> lista = service.myAnimals();
		List<AnimalDTO> listaDto = lista.stream().map(obj -> new AnimalDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);
	}

	@ApiOperation(value = "Busca de animais com paginação")
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<Page<AnimalDTO>> findPage(@RequestParam(value = "uf", defaultValue = "") String uf,
			@RequestParam(value = "municipio", defaultValue = "") String municipio,
			@RequestParam(value = "bairro", defaultValue = "") String bairro,
			@RequestParam(value = "especie", defaultValue = "") String especie,
			@RequestParam(value = "raca", defaultValue = "") String raca,
			@RequestParam(value = "genero", defaultValue = "") String genero,
			@RequestParam(value = "cor", defaultValue = "") String cor,
			@RequestParam(value = "porte", defaultValue = "") String porte,
			@RequestParam(value = "idMicrochip", defaultValue = "") String idMicrochip,
			@RequestParam(value = "esterilizado", defaultValue = "false") Boolean esterilizado,
			@RequestParam(value = "vacinado", defaultValue = "false") Boolean vacinado,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String ufDecoded = Upola.decodeParam(uf);
		String municipioDecoded = Upola.decodeParam(municipio);
		String bairroDecoded = Upola.decodeParam(bairro);
		String especieDecoded = Upola.decodeParam(especie);
		String racaDecoded = Upola.decodeParam(raca);
		String generoDecoded = Upola.decodeParam(genero);
		String corDecoded = Upola.decodeParam(cor);
		String porteDecoded = Upola.decodeParam(porte);
		String idMicrochipDecoded = Upola.decodeParam(idMicrochip);
		Page<Animal> list = service.search(ufDecoded, municipioDecoded, bairroDecoded, especieDecoded, racaDecoded,
				generoDecoded, corDecoded, porteDecoded, idMicrochipDecoded, esterilizado, vacinado, page, linesPerPage,
				orderBy, direction);
		Page<AnimalDTO> listDto = list.map(obj -> new AnimalDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	// inserir imagem
	@ApiOperation(value = "Inserir uma imagem de animal")
	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
			@RequestParam(name = "id") Integer id) {
		URI uri = service.uploadProfilePicture(file, id);
		return ResponseEntity.created(uri).build();
	}

	// inserir multiplas imagens
	@ApiOperation(value = "Inserir varias imagens de animal")
	@RequestMapping(value = "/pictures", method = RequestMethod.POST)
	public List<ResponseEntity<Void>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam(name = "id") Integer id) {
		return Arrays.asList(files).stream().map(file -> uploadProfilePicture(file, id)).collect(Collectors.toList());
	}

	//excluir imagens
	@ApiOperation(value = "Excluir imagem de animal")
	@RequestMapping(value = "/delete/file", method = RequestMethod.DELETE)
	public Map<String, String> deleteFile(@RequestParam("name") String fileName,
			@RequestParam(name = "id") Integer id) {
		service.deleteFileAnimal(id);
		s3Service.deleteFileFromS3Bucket(fileName);
		Map<String, String> response = new HashMap<>();
		response.put("message", "file [" + fileName + "] removing request submitted successfully.");
		return response;
	}

	// meus animais page
		@ApiOperation(value = "Listar meus animais com paginação")
		@RequestMapping(value = "/my", method = RequestMethod.GET)
		public ResponseEntity<Page<AnimalDTO>> myPage(Integer status,@RequestParam(value = "page", defaultValue = "0") Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
			Page<Animal> lista = service.myPage(status,page, linesPerPage, orderBy, direction);
			Page<AnimalDTO> listaDto = lista.map(obj -> new AnimalDTO(obj));
			return ResponseEntity.ok().body(listaDto);
		}
}
