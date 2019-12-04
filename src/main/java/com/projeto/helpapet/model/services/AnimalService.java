package com.projeto.helpapet.model.services;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projeto.helpapet.dto.AnimalDTO;
import com.projeto.helpapet.dto.AnimalNewDTO;
import com.projeto.helpapet.dto.AnimalUpdateDTO;
import com.projeto.helpapet.model.domain.Adocao;
import com.projeto.helpapet.model.domain.Adotante;
import com.projeto.helpapet.model.domain.Animal;
import com.projeto.helpapet.model.domain.ArquivoAnimal;
import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.repositories.AdocaoRepository;
import com.projeto.helpapet.model.repositories.AdotanteRepository;
import com.projeto.helpapet.model.repositories.AnimalRepository;
import com.projeto.helpapet.model.repositories.ArquivoAnimalRepository;
import com.projeto.helpapet.model.repositories.InstituicaoRepository;
import com.projeto.helpapet.model.services.exception.AuthorizationException;
import com.projeto.helpapet.model.services.exception.DataIntegrityException;
import com.projeto.helpapet.model.services.exception.ObjectNotFoundException;
import com.projeto.helpapet.security.UserSS;

@Service
public class AnimalService {

	@Autowired
	public AnimalRepository animalRepository;

	@Autowired
	public ArquivoAnimalRepository arquivoAnimalRepository;

	@Autowired
	public InstituicaoRepository instituicaoRepository;

	@Autowired
	public AdotanteRepository adotanteRepository;

	@Autowired
	public AdocaoRepository adocaoRepository;

	@Autowired
	private S3Service s3Service;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

//inserir
	@Transactional
	public Animal insert(Animal obj) {
		obj = animalRepository.save(obj);
		return obj;
	}

	// transformar objeto
	public Animal fromDTO(AnimalDTO objDto) {
		Animal animal = new Animal(objDto.getId(), null, objDto.getNome(), null, null, null, null, null, null,
				objDto.getEspecie(), null, null, null, null, null);

		return animal;
	}

	public Animal fromDTO(AnimalNewDTO objNewDto) {

		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Instituicao instituicao = instituicaoRepository.getOne(user.getId());

		Animal animal = new Animal(null, objNewDto.getIdMicrochip(), objNewDto.getNome(), objNewDto.getPorte(),
				objNewDto.getGenero(), objNewDto.getVacinado(), objNewDto.getAnoNascimento(), objNewDto.getRaca(),
				objNewDto.getVermifugado(), objNewDto.getEspecie(), objNewDto.getDataRecolhimento(),
				objNewDto.getEsterilizado(), objNewDto.getCor(), objNewDto.getDescricao(), instituicao);
		return animal;
	}

	public Animal fromDTO(AnimalUpdateDTO objUpdateDto) {

		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Instituicao instituicao = instituicaoRepository.getOne(user.getId());
		Animal animal = new Animal(null, objUpdateDto.getIdMicrochip(), objUpdateDto.getNome(), objUpdateDto.getPorte(),
				objUpdateDto.getGenero(), objUpdateDto.getVacinado(), objUpdateDto.getAnoNascimento(),
				objUpdateDto.getRaca(), objUpdateDto.getVermifugado(), objUpdateDto.getEspecie(),
				objUpdateDto.getDataRecolhimento(), objUpdateDto.getEsterilizado(), objUpdateDto.getCor(),
				objUpdateDto.getDescricao(), instituicao);
		return animal;
	}

	// buscar por id
	public Animal find(Integer id) {
		Optional<Animal> obj = animalRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Animal.class.getName()));
	}

//altera
	public Animal update(Animal obj) {
		Animal newObj = find(obj.getId());
		updateDate(newObj, obj);
		newObj = animalRepository.save(newObj);
		return newObj;
	}

	private void updateDate(Animal newObj, Animal obj) {
		newObj.setNome(obj.getNome());
		newObj.setDescricao(obj.getDescricao());
		newObj.setEsterilizado(obj.getEsterilizado());
		newObj.setPorte(obj.getPorte());
		newObj.setVermifugado(obj.getVermifugado());
		newObj.setVacinado(obj.getVacinado());
		newObj.setIdMicrochip(obj.getIdMicrochip());
	}

//excluir
	public void delete(Integer id) {
		find(id);
		try {
			animalRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

//busca todos animal
	public List<Animal> findAll() {
		return animalRepository.findAll();
	}

//paginação
	public Page<Animal> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return animalRepository.findAll(pageRequest);
	}

	// listar pagina de animais disponiveis
	public Page<Animal> findAllAvailable(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return animalRepository.findAllAvailable(pageRequest);
	}

//pagina meus animais
	public Page<Animal> myAnimalsPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		UserSS user = UserService.authenticated();
		Instituicao inst = instituicaoRepository.getOne(user.getId());
		Integer id = inst.getIdUsuario();
		return animalRepository.findAllAnimalPage(id, pageRequest);
	}

	// meus animal
	public List<Animal> myAnimals() {
		UserSS user = UserService.authenticated();
		Instituicao inst = instituicaoRepository.getOne(user.getId());
		Integer id = inst.getIdUsuario();
		return animalRepository.findAllAnimal(id);
	}

//busca paginada
	public Page<Animal> search(String uf, String municipio, String bairro, String especie, String raca, String genero,
			String cor, String porte, String idMicrochip, Boolean esterilizado, Boolean vacinado, Integer page,
			Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return animalRepository.search(uf, municipio, bairro, especie, raca, genero, cor, porte, idMicrochip,
				esterilizado, vacinado, pageRequest);
	}

//imagem
	public URI uploadProfilePicture(MultipartFile multipartFile, Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		URI uri = s3Service.uploadFile(multipartFile);
		Animal animal = new Animal();
		animal = animalRepository.getOne(id);
		ArquivoAnimal arquivoAnimal = new ArquivoAnimal(null, animal, uri.toString());
		// animal.getArquivoAnimalList().add(arquivoAnimal);
		animalRepository.save(animal);
		arquivoAnimalRepository.save(arquivoAnimal);
		return uri;
	}

	public void deleteFileAnimal(Integer id) {
		arquivoAnimalRepository.getOne(id);
		try {
			arquivoAnimalRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

	// inserir adocao
	@Transactional
	public Adocao insert(Adocao obj) {
		obj = adocaoRepository.save(obj);
		return obj;

	}

	public Adocao fromAdotar(Animal objAnimal) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Adotante adotante = adotanteRepository.getOne(user.getId());
		Adocao adocao = new Adocao(null, 1, new Date(), null, objAnimal, adotante);
		return adocao;
	}

	// pagina meus animais
	public Page<Animal> myPage(Integer status, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		UserSS user = UserService.authenticated();
		Instituicao inst = instituicaoRepository.getOne(user.getId());
		Integer id = inst.getIdUsuario();
		return animalRepository.findMy(status, id, pageRequest);
	}
}
