package com.fatec.API3.controller;

import com.lowagie.text.DocumentException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class CertificadoController {
	
	
	@GetMapping("/certificados")
	public String certificado() {
		return "/certificado";
	}
	
	@PostMapping("/gerarcertificado")
	
	public String gerarcertificado(String nome, String curso) throws DocumentException, IOException {
		CertificadoController certificado = new CertificadoController();
		String Nome  = nome;
		String Imagem = "../assets/ci_1.png";
		String Curso = curso;
		String html = certificado.parseThymeleafTemplate(Nome, Curso, Imagem);
		certificado.generatePdfFromHtml(html);
		System.out.print("Deu tudo certo");
		
		return "redirect:certificados";
	}
	
	 public String parseThymeleafTemplate(String Nome, String Curso, String Imagem) {
	        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	        templateResolver.setPrefix("templates/");
	        templateResolver.setSuffix(".html");
	        templateResolver.setTemplateMode("HTML5");
	        templateResolver.setCharacterEncoding("UTF-8");
	        TemplateEngine templateEngine = new TemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver);

	        Context context = new Context();
	        context.setVariable("imagem", Imagem);
	        context.setVariable("nome", Nome);
	        context.setVariable("curso", Curso);

	        return templateEngine.process("certificado", context);
	    }
	
	
	public void generatePdfFromHtml(String html) throws DocumentException, IOException {
		
	    String outputFolder = System.getProperty("user.home") + File.separator + "Certificado.pdf";
	    OutputStream outputStream = new FileOutputStream(outputFolder);

	    ITextRenderer renderer = new ITextRenderer();
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    outputStream.close();
	}

}
