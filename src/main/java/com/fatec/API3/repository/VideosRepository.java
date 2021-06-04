package com.fatec.API3.repository;

import org.springframework.data.repository.CrudRepository;

import com.fatec.API3.model.Cursos;
import com.fatec.API3.model.Videos;

public interface VideosRepository extends CrudRepository<Videos, String> {

	Videos findByid(long id);
	
	Iterable<Videos> findByCurso(Cursos cursos);
	
	Videos findById(long id);
	
}