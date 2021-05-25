package com.fatec.API3.repository;

import org.springframework.data.repository.CrudRepository;

import com.fatec.API3.model.Gestor;

public interface GestorRepository extends CrudRepository<Gestor, String> {

	Gestor findByid(long id);
	
	Gestor findByemail(String email);
	
	boolean existsByemail(String email);
}
