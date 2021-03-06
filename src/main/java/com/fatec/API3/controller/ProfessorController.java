package com.fatec.API3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.API3.model.Alunos;
import com.fatec.API3.model.Cursos;
import com.fatec.API3.model.Professor;
import com.fatec.API3.repository.AlunosRepository;
import com.fatec.API3.repository.CursosRepository;
import com.fatec.API3.repository.ProfessorRepository;

@Controller
@RequestMapping("/")
public class ProfessorController {
	
	@Autowired
	private ProfessorRepository PR;
	
	@Autowired
	private CursosRepository cr;
	
	@Autowired
	private AlunosRepository ar;
	
	//private static String caminhoimagens = "C:\\Users\\kiabi\\OneDrive\\Documentos\\Imagens_API3\\";
	private static String caminhoimagens = "C:\\Users\\Pichau\\Documents\\Imagens_API3\\";

	
	@RequestMapping("/gerarcertificadog")
	public ModelAndView gerarpdf() {
		ModelAndView mv = new ModelAndView ("/home/gerarcertificado");
		Iterable<Alunos> alunos = ar.findAll();
		mv.addObject("alunos", alunos);
		Iterable<Cursos> cursos = cr.findAll();
		mv.addObject("cursos", cursos);
		return mv;
	}
	
	@RequestMapping("/homeprofessor")
	public ModelAndView listaCursos() {
		ModelAndView mv = new ModelAndView("home/homeprofessor");
		Iterable<Cursos> cursos = cr.findAll();
		mv.addObject("cursos", cursos);
		return mv;
	}
	
	@RequestMapping("/cursos")
	public ModelAndView listadeCursos() {
		ModelAndView mv = new ModelAndView("home/cursos");
		Iterable<Cursos> cursos = cr.findAll();
		mv.addObject("cursos", cursos);
		return mv;
	}
	
	@GetMapping("/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoimagens+imagem);
		if (imagem != null) {
			return Files.readAllBytes(imagemArquivo.toPath());
		} 
		return null;
	}
	
	
	@GetMapping("/cadastroprofessor")
	public String cadastro(){
		return "/entrada/cadastroprofessor"; 
	}  

	@PostMapping("/cadastroprofessor")
	//Verificando se o email j?? est?? cadastrado.
	public String cadastroprofessorp(Professor professor){
		boolean existe = PR.existsByemail(professor.getEmail());
		if(existe) {return "redirect:cadastroprofessor";}
		String senha = professor.getSenha();
		String senhaenc = new BCryptPasswordEncoder().encode(senha);
		professor.setSenha(senhaenc);
		//Colocando propriedades para o envio de email.
				Properties props = new Properties();
			    /** Par??metros de conex??o com servidor Gmail */
				
			    props.put("mail.smtp.host", "smtp.gmail.com");
			    props.put("mail.smtp.socketFactory.port", "465");
			    props.put("mail.smtp.socketFactory.class",
			    "javax.net.ssl.SSLSocketFactory");
			    props.put("mail.smtp.auth", "true");
			    props.put("mail.smtp.port", "465");

			    Session session = Session.getInstance(props,
			      new javax.mail.Authenticator() {
			           protected PasswordAuthentication getPasswordAuthentication()
			           {
			                 return new PasswordAuthentication("timeslim123@gmail.com",
			                 "@Timeslim321");
			           }
			      });

			    /** Ativa Debug para sess??o */
			    session.setDebug(true);
			    
			    //Enviando email com os dados revertidos.
			    try {

			      Message message = new MimeMessage(session);
			      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
			      //Remetente

			      Address[] toUser = InternetAddress //Destinat??rio(s)
			                 .parse(professor.getEmail());

			      message.setRecipients(Message.RecipientType.TO, toUser);
			      message.setSubject("Bem vindo a Neduc");//Assunto
			      message.setText("Email enviado para confirma????o do seu cadastro em nossa plataforma! \n Muito Obrigado!");
			      /**M??todo para enviar a mensagem criada*/
			      Transport.send(message);

			      System.out.println("Mensagem enviada!!!");

			     } catch (MessagingException e) {
			        throw new RuntimeException(e);}
				PR.save(professor);
				return "redirect:loginprofessor";
			}
	
	
	
}

