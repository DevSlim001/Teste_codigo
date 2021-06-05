package com.fatec.API3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.API3.model.Cursos;
import com.fatec.API3.model.Tarefas;
import com.fatec.API3.model.Videos;
import com.fatec.API3.repository.CursosRepository;
import com.fatec.API3.repository.TarefasRepository;
import com.fatec.API3.repository.VideosRepository;

@Controller
public class TarefasController {

	@Autowired
	private TarefasRepository tr;
	
	@Autowired
	private CursosRepository cr;
	
	@Autowired
	private VideosRepository vr;
	
	
	@RequestMapping(value="/detalhes/{id}", method=RequestMethod.GET)
	public ModelAndView detalhes(@PathVariable("id") long id){
		Cursos curso = cr.findByid(id);
		ModelAndView mv = new ModelAndView("home/detalhes");
		mv.addObject("curso", curso);
		Iterable<Tarefas> tarefas = tr.findByCurso(curso);
		mv.addObject("tarefas", tarefas);
		Iterable<Videos> videos = vr.findByCurso(curso);
		mv.addObject("videos", videos);
		return mv;
	}

	
	@RequestMapping(value="/tarefa/{id}", method=RequestMethod.GET)
	public ModelAndView verTarefa(@PathVariable("id") long id) {
		Tarefas tarefa = tr.findById(id);
		ModelAndView mv = new ModelAndView("home/form");
		mv.addObject("tarefa", tarefa);
		return mv;
	}
	
	
}
