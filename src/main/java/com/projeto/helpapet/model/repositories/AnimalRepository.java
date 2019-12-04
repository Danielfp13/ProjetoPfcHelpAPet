package com.projeto.helpapet.model.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.helpapet.model.domain.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
	@Transactional(readOnly = true)
	Animal findByIdMicrochip(String idMicrochip);

	@Query( value="SELECT * FROM animal a where a.id_instituicao_fk = :id ",nativeQuery=true )
	Page<Animal> findAllAnimalPage(@Param("id") Integer id, Pageable pageRequest);

	@Query(value = "SELECT * FROM animal a where a.id_instituicao_fk = ?1 ", nativeQuery = true)
	List<Animal> findAllAnimal(Integer id);
	

	@Query( value="select distinct * from animal a\n" + 
			"where  a.id not in\n" + 
			"(\n" + 
			" select distinct ad.id_animal_fk from adocao ad\n" + 
			" where ad.status in (1 , 2)\n" + 
			")",nativeQuery=true )
	Page<Animal> findAllAvailable(Pageable pageRequest);
	
	@Query(value = "select * from animal a inner join instituicao i on i.id_usuario = a.id_instituicao_fk inner join "
			+ "usuario u on u.id_usuario = i.id_usuario	where u.uf ilike %:uf% and u.municipio \n"
			+ "ilike %:municipio% and u.bairro ilike %:bairro% and a.especie ilike %:especie% and a.raca ilike %:raca% "
			+ "and a.genero ilike %:genero%  and a.cor ilike %:cor%  and a.porte ilike %:porte%  and a.id_microchip ilike %:idMicrochip% "
			+ "and a.esterilizado = :esterilizado and a.vacinado = :vacinado", nativeQuery = true)
	Page<Animal> search(@Param("uf") String uf, @Param("municipio") String municipio, @Param("bairro") String bairro,
			@Param("especie") String especie, @Param("raca") String raca, @Param("genero") String genero,
			@Param("cor") String cor, @Param("porte") String porte,@Param("idMicrochip")String idMicrochip, @Param("esterilizado")	Boolean esterilizado,
			@Param("vacinado") Boolean vacinado, Pageable pageRequest);
	
	
	
	@Query( value="SELECT * FROM animal a "
			+ "inner join adocao ad on ad.id_animal_fk = a.id"
			+ " where a.id_instituicao_fk = :id and ad.status = :status ",nativeQuery=true )
	Page<Animal> findMy(@Param("status") Integer status,@Param("id") Integer id, Pageable pageRequest);
	
	
}