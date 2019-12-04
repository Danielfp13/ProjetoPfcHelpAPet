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

import com.projeto.helpapet.dto.InstituicaoDTO;
import com.projeto.helpapet.dto.InstituicaoNewDTO;
import com.projeto.helpapet.dto.InstituicaoUpdateDTO;
import com.projeto.helpapet.model.domain.Instituicao;
import com.projeto.helpapet.model.domain.Usuario;
import com.projeto.helpapet.model.domain.enums.Perfil;
import com.projeto.helpapet.model.repositories.InstituicaoRepository;
import com.projeto.helpapet.model.repositories.UsuarioRepository;
import com.projeto.helpapet.model.services.exception.AuthorizationException;
import com.projeto.helpapet.model.services.exception.DataIntegrityException;
import com.projeto.helpapet.model.services.exception.ObjectNotFoundException;
import com.projeto.helpapet.security.UserSS;

@Service
public class InstituicaoService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	public InstituicaoRepository instituicaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

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

	// inserir
	@Transactional
	public Instituicao insert(Instituicao obj) {
		obj = instituicaoRepository.save(obj);
		emailService.sendOrderConfirmationEmail(obj);
		List<Usuario> usuarioList = usuarioRepository.findAll();
		for (Usuario usuario : usuarioList) {
			for (Perfil perfil : usuario.getPerfil()) {
				if (perfil.getCod() == 1) {
					emailService.sendReleaseRegister(obj, usuario);
				}
			}
		}
		return obj;
	}

	// transformar objeto
	public Instituicao fromDTO(InstituicaoDTO objDto) {
		Instituicao instituicao = new Instituicao(objDto.getIdUsuario(), objDto.getNome(), objDto.getEmail(), null,
				null, null, null, null, null, null, null, null, null, objDto.getFoto(), null, null, null, null, null,
				null);

		return instituicao;
	}

	public Instituicao fromDTO(InstituicaoNewDTO objDto) {

		Instituicao instituicao = new Instituicao(objDto.getIdUsuario(), objDto.getNome(), objDto.getEmail(),
				new Date(), new Date(), true, pe.encode(objDto.getSenha()), objDto.getMunicipio(), objDto.getCep(),
				objDto.getUf(), objDto.getBairro(), objDto.getLogradouro(), objDto.getNumero(), objDto.getFoto(),
				objDto.getCnpj(), 1, objDto.getDescricaoInstituicao(), objDto.getTermo(),
				objDto.getTelefone1(), objDto.getTelefone2());
		return instituicao;
	}

	public Instituicao fromDTO(InstituicaoUpdateDTO objUpdateDto) {
		Instituicao instituicao = new Instituicao(objUpdateDto.getIdUsuario(), objUpdateDto.getNome(),
				objUpdateDto.getEmail(), new Date(), new Date(), true, null, objUpdateDto.getMunicipio(),
				objUpdateDto.getCep(), objUpdateDto.getUf(), objUpdateDto.getBairro(), objUpdateDto.getLogradouro(),
				objUpdateDto.getNumero(), objUpdateDto.getFoto(), objUpdateDto.getCnpj(), 1,
				objUpdateDto.getDescricaoInstituicao(), true, objUpdateDto.getTelefone1(), objUpdateDto.getTelefone2());
		return instituicao;
	}

	// buscar por id
	public Instituicao find(Integer id) {

		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Instituicao> obj = instituicaoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Instituicao.class.getName()));
	}

//altera
	public Instituicao update(Instituicao obj) {
		Instituicao newObj = find(obj.getIdUsuario());
		updateDate(newObj, obj);
		newObj = instituicaoRepository.save(newObj);
		return newObj;
	}

	private void updateDate(Instituicao newObj, Instituicao obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setBairro(obj.getBairro());
		newObj.setCep(obj.getCep());
		newObj.setDataAtualizacao(obj.getDataAtualizacao());
		newObj.setMunicipio(obj.getMunicipio());
		newObj.setNumero(obj.getNumero());
		newObj.setLogradouro(obj.getLogradouro());
		newObj.setUf(obj.getUf());
		newObj.setAtivo(obj.getAtivo());
		newObj.setTelefone1(obj.getTelefone1());
		newObj.setTelefone2(obj.getTelefone2());
	}

//excluir
	public void delete(Integer id) {
		find(id);
		try {
			instituicaoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

//busca todos instituicao
	public List<Instituicao> findAll() {
		return instituicaoRepository.findAll();
	}

//paginação
	public Page<Instituicao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return instituicaoRepository.findAll(pageRequest);
	}

//imagem

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		URI uri = s3Service.uploadFile(multipartFile);
		Instituicao instituicao = instituicaoRepository.getOne(user.getId());
		instituicao.setFoto(uri.toString());
		instituicaoRepository.save(instituicao);

		return uri;
	}

	// busca instituicao pendente
	public List<Instituicao> findPending() {
		return instituicaoRepository.findAllSituacaoCadastro();
	}

//liberar acesso

	public void releaseAccess(Integer id) {
		Instituicao obj = new Instituicao();
		obj = find(id);
		obj.addPerfil(Perfil.INSTITUICAO);
		obj.setSituacaoCadastro(2);
		instituicaoRepository.save(obj);
		emailService.sendOrderConfirmation(obj);
		return;
	}


}
