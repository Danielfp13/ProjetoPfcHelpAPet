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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projeto.helpapet.dto.AdotanteDTO;
import com.projeto.helpapet.dto.AdotanteNewDTO;
import com.projeto.helpapet.dto.AdotanteUpdateDTO;
import com.projeto.helpapet.model.domain.Adotante;
import com.projeto.helpapet.model.repositories.AdotanteRepository;
import com.projeto.helpapet.model.services.exception.AuthorizationException;
import com.projeto.helpapet.model.services.exception.DataIntegrityException;
import com.projeto.helpapet.model.services.exception.ObjectNotFoundException;
import com.projeto.helpapet.security.UserSS;

@Service
public class AdotanteService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	public AdotanteRepository adotanteRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

//inserir

	@Transactional
	public Adotante insert(Adotante obj) {
		obj = adotanteRepository.save(obj);
		emailService.sendOrderConfirmationEmail(obj);
		return obj;

	}

	// transformar objeto
	public Adotante fromDTO(AdotanteDTO objDto) {
		Adotante adotante = new Adotante(objDto.getIdUsuario(), objDto.getNome(), objDto.getEmail(), null, null, null,
				null, null, null, null, null, null, null, objDto.getFoto(), null, null, null, null, null, null, null);

		return adotante;
	}

	public Adotante fromDTO(AdotanteNewDTO objDto) {

		Adotante adotante = new Adotante(objDto.getIdUsuario(), objDto.getNome(), objDto.getEmail(), new Date(),
				new Date(), true, pe.encode(objDto.getSenha()), objDto.getMunicipio(), objDto.getCep(), objDto.getUf(),
				objDto.getBairro(), objDto.getLogradouro(), objDto.getNumero(), objDto.getFoto(), objDto.getCpf(),
				objDto.getRg(), objDto.getDataNascimento(), objDto.getTermo(), objDto.getTelefone1(),
				objDto.getTelefone2(), objDto.getSobrenome());

		return adotante;

	}

	public Adotante fromDTO(AdotanteUpdateDTO objDto) {

		Adotante adotante = new Adotante(objDto.getIdUsuario(), objDto.getNome(), objDto.getEmail(), new Date(), null,
				true, objDto.getSenha(), objDto.getMunicipio(), objDto.getCep(), objDto.getUf(), objDto.getBairro(),
				objDto.getLogradouro(), objDto.getNumero(), objDto.getFoto(), objDto.getCpf(), objDto.getRg(),
				objDto.getDataNascimento(), null, objDto.getTelefone1(), objDto.getTelefone2(), objDto.getSobrenome());
		return adotante;
	}

	// buscar por id
	public Adotante find(Integer id) {
		Optional<Adotante> obj = adotanteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Adotante.class.getName()));
	}

//altera
	public Adotante update(Adotante obj) {
		Adotante newObj = find(obj.getIdUsuario());
		updateDate(newObj, obj);
		newObj = adotanteRepository.save(newObj);
		return newObj;
	}

	private void updateDate(Adotante newObj, Adotante obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setBairro(obj.getBairro());
		newObj.setCep(obj.getCep());
		newObj.setDataAtualizacao(new Date());
		newObj.setMunicipio(obj.getMunicipio());
		newObj.setNumero(obj.getNumero());
		newObj.setLogradouro(obj.getLogradouro());
		newObj.setUf(obj.getUf());
		newObj.setSenha(obj.getSenha());
		newObj.setTelefone1(obj.getTelefone1());
		newObj.setTelefone2(obj.getTelefone2());
	}

//excluir
	public void delete(Integer id) {
		find(id);
		try {
			adotanteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

//busca todos adotante
	public List<Adotante> findAll() {
		return adotanteRepository.findAll();
	}

//paginação
	public Page<Adotante> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return adotanteRepository.findAll(pageRequest);
	}

	// imagem
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		URI uri = s3Service.uploadFile(multipartFile);
		Adotante adotante = adotanteRepository.getOne(user.getId());
		adotante.setFoto(uri.toString());
		adotanteRepository.save(adotante);

		return uri;
	}
}
