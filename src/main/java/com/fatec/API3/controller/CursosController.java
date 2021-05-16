package com.fatec.API3.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.API3.model.Cursos;
import com.fatec.API3.repository.CursosRepository;

@Controller
public class CursosController {
	
	private static String caminhoimagens = "C:\\Users\\kiabi\\OneDrive\\Documentos\\Imagens_API3";

	@Autowired
	private CursosRepository cr;
	
	@GetMapping("/cadastrocurso")
	public String cadastro(){
		return "home/novocurso"; 
	}
	
	@PostMapping("/cadastrocurso")
	public String cadastro(Cursos curso, @RequestParam("file") MultipartFile arquivo, MultipartFile padrao) throws IOException {
		if (!arquivo.isEmpty()) {
			byte[] bytes = arquivo.getBytes();
			Path caminho = Paths
					.get(caminhoimagens + arquivo.getOriginalFilename());
			Files.write(caminho, bytes);

			curso.setImagem(arquivo.getOriginalFilename());
		} else {
			
		}
		cr.save(curso);
		return "home/novocurso"; 
	}
	
}
