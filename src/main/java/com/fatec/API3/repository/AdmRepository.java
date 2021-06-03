package com.fatec.API3.repository;

import org.springframework.data.repository.CrudRepository;

import com.fatec.API3.model.Administrador;

public interface AdmRepository extends CrudRepository<Administrador, String> {

	Administrador findByid(long id);
	
	Administrador findByemail(String email);
	
	Administrador findBycodigosenha(String codigosenha);
	
	boolean existsByemail(String email);
}
