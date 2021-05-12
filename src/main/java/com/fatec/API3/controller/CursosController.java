package com.fatec.API3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fatec.API3.model.Cursos;
import com.fatec.API3.repository.CursosRepository;

@Controller
public class CursosController {

	@Autowired
	private CursosRepository cr;
	
	@GetMapping("/cadastrocurso")
	public String cadastro(){
		return "cadastrocurso"; 
	}
	
	@PostMapping("/cadastrocurso")
	public String cadastro(Cursos curso){
		cr.save(curso);
		return "redirect:/homeprofessor"; 
	}
}
