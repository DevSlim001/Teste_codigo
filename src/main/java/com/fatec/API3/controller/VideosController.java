package com.fatec.API3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fatec.API3.repository.CursosRepository;
import com.fatec.API3.repository.VideosRepository;

@Controller
public class VideosController {

	@Autowired
	private VideosRepository vr;
	
	@Autowired
	private CursosRepository cr;
	
	
}