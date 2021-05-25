package com.fatec.API3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InicioController {

	@GetMapping("/index")
	public String index(){
		return "index"; 
	}
	
	@GetMapping("/help")
	public String help(){
		return "/help/paghelp"; 
	}  
	
	@GetMapping("/divcad")
	public String divcad(){
		return "divcad";
	} 
	
	@GetMapping("/div")
	public String div(){
		return "/div";
	}
}
