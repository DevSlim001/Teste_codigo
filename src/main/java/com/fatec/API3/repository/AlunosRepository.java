package com.fatec.API3.repository;

import org.springframework.data.repository.CrudRepository;

import com.fatec.API3.model.Alunos;

public interface AlunosRepository extends CrudRepository<Alunos, String>{
	
	Alunos findByid(long id);
	
	Alunos findByemail(String email);
	
	Alunos findBycodigosenha(String codigosenha);
	
	boolean existsByemail(String email);
}
