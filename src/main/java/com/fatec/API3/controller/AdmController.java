package com.fatec.API3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.API3.model.Alunos;
import com.fatec.API3.model.Cursos;
import com.fatec.API3.repository.AlunosRepository;
import com.fatec.API3.repository.CursosRepository;

@Controller
public class AdmController {
	@Autowired
	private CursosRepository cr;
	
	@Autowired
	private AlunosRepository ar;
	
	//private static String caminhoimagens = "C:\\Users\\kiabi\\OneDrive\\Documentos\\Imagens_API3\\";
		private static String caminhoimagens = "C:\\Users\\Pichau\\Documents\\Imagens_API3\\";

	
	@RequestMapping("/homeadm")
	public ModelAndView homeadm() {
		ModelAndView mv = new ModelAndView ("home/homeadm");
		Iterable<Alunos> alunos = ar.findAll();
		mv.addObject("alunos", alunos);
		Iterable<Cursos> cursos = cr.findAll();
		mv.addObject("cursos", cursos);
		return mv;
	}
	
	@GetMapping("/curso/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoimagens+imagem);
		if (imagem != null) {
			return Files.readAllBytes(imagemArquivo.toPath());
		} 
		return null;
	}
}
