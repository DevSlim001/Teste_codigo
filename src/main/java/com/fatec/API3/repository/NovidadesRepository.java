package com.fatec.API3.repository;

import org.springframework.data.repository.CrudRepository;

import com.fatec.API3.model.Novidades;

public interface NovidadesRepository extends CrudRepository<Novidades, String>{

	Novidades findByid(long id);
}
