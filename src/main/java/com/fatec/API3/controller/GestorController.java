package com.fatec.API3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.API3.model.Alunos;
import com.fatec.API3.model.Cursos;
import com.fatec.API3.model.Novidades;
import com.fatec.API3.repository.AlunosRepository;
import com.fatec.API3.repository.CursosRepository;
import com.fatec.API3.repository.NovidadesRepository;

@Controller
public class GestorController {
	
	@Autowired
	private CursosRepository cr;
	
	@Autowired
	private AlunosRepository ar;
	
	@Autowired
	private NovidadesRepository nr;
	
	//private static String caminhoimagens = "C:\\Users\\kiabi\\OneDrive\\Documentos\\Imagens_API3\\";
		private static String caminhoimagens = "C:\\Users\\Pichau\\Documents\\Imagens_API3\\";

		private static String caminhopadrao = "padrao.png";

	@RequestMapping("/homegestor")
	public ModelAndView homegestor() {
		ModelAndView mv = new ModelAndView ("home/homegestor");
		Iterable<Novidades> novidades = nr.findAll();
		mv.addObject("novidades", novidades);
		Iterable<Alunos> alunos = ar.findAll();
		mv.addObject("alunos", alunos);
		Iterable<Cursos> cursos = cr.findAll();
		mv.addObject("cursos", cursos);
		return mv;
	}
}
