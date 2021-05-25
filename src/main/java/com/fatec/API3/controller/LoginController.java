package com.fatec.API3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatec.API3.model.Administrador;
import com.fatec.API3.model.Alunos;
import com.fatec.API3.model.Gestor;
import com.fatec.API3.model.Professor;
import com.fatec.API3.model.Usuario;
import com.fatec.API3.repository.AdmRepository;
import com.fatec.API3.repository.AlunosRepository;
import com.fatec.API3.repository.GestorRepository;
import com.fatec.API3.repository.ProfessorRepository;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private AlunosRepository AR;
	@Autowired
	private GestorRepository GR;
	@Autowired
	private ProfessorRepository PR;
	@Autowired
	private AdmRepository ADMR;
	
	@GetMapping("/login")
	public String Login() {
		return "/login";
	}
	
	@ExceptionHandler
	@PostMapping("/login")
	public String login(String email, String senha) {
		
		Alunos aluno = AR.findByemail(email);
		Professor professor = PR.findByemail(email);
		Gestor gestor = GR.findByemail(email);
		Administrador adm = ADMR.findByemail(email);
		
		if(aluno == null && professor == null && gestor == null && adm == null) {
			return "Nenhum usuário com esse email foi encontrado";
		}
		
		if(aluno != null && aluno.getEmail().equals(email) && aluno.getSenha().equals(senha)) {
			Usuario.tipoUsu = "aluno";
			Usuario.idUsu = aluno.getId();
			Usuario.nomeUsu = aluno.getNome();
			return "redirect:homealuno";
		}
		
		if (professor != null && professor.getEmail().equals(email) && professor.getSenha().equals(senha)) {
			Usuario.tipoUsu = "professor";
			Usuario.idUsu = professor.getId();
			Usuario.nomeUsu = professor.getNome();
			return "redirect:homeprofessor";
		}
		
		if(adm != null && adm.getEmail().equals(email) && adm.getSenha().equals(senha)) {
			Usuario.tipoUsu = "ADM";
			Usuario.idUsu = adm.getId();
			Usuario.nomeUsu = adm.getNome();
			return "redirect:homeadm";
		}
		
		if(gestor != null && gestor.getEmail().equals(email) && gestor.getSenha().equals(senha)) {
			Usuario.tipoUsu = "gestor";
			Usuario.idUsu = gestor.getId();
			Usuario.nomeUsu = gestor.getNome();
			return "redirect:homegestor";
		}
		
		return null;
	}
	
	@GetMapping("/logout")
	public String sair() {
		Usuario.tipoUsu = "";
		Usuario.idUsu = null;
		return "redirect:index";
	}
	
	
}
