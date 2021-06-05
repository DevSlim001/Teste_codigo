package com.fatec.API3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VideosController {

	private static String caminhoimagens = "C:\\Users\\kiabi\\OneDrive\\Documentos\\Imagens_API3\\";
	//private static String caminhoimagens = "C:\\Users\\Pichau\\Documents\\Imagens_API3\\";
	
	
	@GetMapping("/video/{video}")
	@ResponseBody
	public byte[] verVideo(@PathVariable("video") String video) throws IOException {
		File imagemArquivo = new File(caminhoimagens+video);
		if (video != null) {
			return Files.readAllBytes(imagemArquivo.toPath());
		} 
		return null;
	}
	
	
	
}