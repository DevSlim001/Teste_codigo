package com.fatec.API3.repository;

import org.springframework.data.repository.CrudRepository;

import com.fatec.API3.model.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, String> {
	Professor findByid(long id);
	
	
	Professor findByemail(String email);
	
	Professor findBycodigosenha(String codigosenha);
	
	boolean existsByemail(String email);
}
