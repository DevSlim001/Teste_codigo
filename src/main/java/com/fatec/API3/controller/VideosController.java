package com.fatec.API3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.API3.model.Videos;
import com.fatec.API3.repository.VideosRepository;

@Controller
public class VideosController {

	private static String caminhoimagens = "C:\\Users\\kiabi\\OneDrive\\Documentos\\Imagens_API3\\";
	//private static String caminhoimagens = "C:\\Users\\Pichau\\Documents\\Imagens_API3\\";
	
	@Autowired
	private VideosRepository vr;
	
	@GetMapping(value="/video/{id}")
	public ModelAndView video(@PathVariable("id") long id) {
		Videos video = vr.findById(id);
		ModelAndView mv = new ModelAndView("home/video");
		mv.addObject("video", video);
		return mv;
	}
	
	
	@GetMapping("/videoarquivo/{video}")
	@ResponseBody
	public byte[] verVideo(@PathVariable("video") String video) throws IOException {
		File videoArquivo = new File(caminhoimagens+video);
		if (video != null) {
			return Files.readAllBytes(videoArquivo.toPath());
		} 
		return null;
	}
	
}