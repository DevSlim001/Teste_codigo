package com.fatec.API3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fatec.API3.model.Tarefas;
import com.fatec.API3.repository.TarefasRepository;

@Controller
public class TarefasController {

	@Autowired
	private TarefasRepository tr;
	
	@GetMapping("/novatarefa")
	public String cadastro(){
		return "home/novatarefa"; 
	}
	
	@PostMapping("/novatarefa")
	public String cadastro(Tarefas tarefa){
		tr.save(tarefa);
		return "redirect:/home/novatarefa"; 
	}
}
