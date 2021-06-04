package com.fatec.API3.controller;

import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.API3.model.Administrador;
import com.fatec.API3.model.Alunos;
import com.fatec.API3.model.Gestor;
import com.fatec.API3.model.Professor;
import com.fatec.API3.repository.AdmRepository;
import com.fatec.API3.repository.AlunosRepository;
import com.fatec.API3.repository.GestorRepository;
import com.fatec.API3.repository.ProfessorRepository;

@Controller
public class SenhaController {
	
	@Autowired
	private AlunosRepository AR;
	@Autowired
	private GestorRepository GR;
	@Autowired
	private ProfessorRepository PR;
	@Autowired
	private AdmRepository ADMR;

	@GetMapping("/recuperarsenha")
	public String recuperarsenha(){
		return "/entrada/recuperarsenha";
	} 
	
	@GetMapping("/recuperarsenha/codigo")
	public String recuperarsenhacodigo() {
		return "/entrada/codigosenha";
	}

	@PostMapping("/codigosenha")
	public String codigosenha(String codigosenha, String email, String senha) {
		Alunos aluno = AR.findBycodigosenha(codigosenha);
		Professor professor = PR.findBycodigosenha(codigosenha);
		Gestor gestor = GR.findBycodigosenha(codigosenha);
		Administrador adm = ADMR.findBycodigosenha(codigosenha);
		
		if (aluno == null && professor == null && gestor == null && adm == null) {
			return "Código inválido";
		}
		
		if (aluno != null && aluno.getCodigosenha().equals(codigosenha) && aluno.getEmail().equals(email)) {
			aluno.setSenha(senha);
			AR.save(aluno);
			return "/login";
		}
		
		if (professor != null && professor.getCodigosenha().equals(codigosenha) && professor.getEmail().equals(email)) {
			professor.setSenha(senha);
			PR.save(professor);
			return "/login";
		}
		
		if (adm != null && adm.getCodigosenha().equals(codigosenha) && adm.getEmail().equals(email)) {
			adm.setSenha(senha);
			ADMR.save(adm);
			return "/login";
		}
		
		if (gestor != null && gestor.getCodigosenha().equals(codigosenha) && gestor.getEmail().equals(email)) {
			gestor.setSenha(senha);
			GR.save(gestor);
			return "/login";
		}
		
	return null;
	}
	
	@PostMapping("/recuperarsenha")
	public String recuperarsenha(String email){
		Random random = new Random();
		int aleatório = random.nextInt(10000) + 1000;
		String numero = Integer.toString(aleatório);
		Alunos aluno = AR.findByemail(email);
		Professor professor = PR.findByemail(email);
		Gestor gestor = GR.findByemail(email);
		Administrador adm = ADMR.findByemail(email);
		if (aluno == null && professor == null && gestor == null && adm == null ) {
			return "redirect:index";
		}
		if(aluno != null && aluno.getEmail().equals(email)) {
		aluno.setCodigosenha(numero);
		AR.save(aluno);
		Properties props = new Properties();
	    /** Parâmetros de conexão com servidor Gmail */
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
	    /** Ativa Debug para sessão */
	    session.setDebug(true);
	    //Enviando email com os dados revertidos.
	    try {
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
	      //Remetente
	      Address[] toUser = InternetAddress //Destinatário(s)
	                 .parse(aluno.getEmail());
	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject("Recuperação de senha Neduc");//Assunto
	      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + aluno.getCodigosenha(); 
	      message.setText(texto);
	      /**Método para enviar a mensagem criada*/
	      Transport.send(message);
	      System.out.println("Mensagem enviada!!!");
	     } catch (MessagingException e) {
	        throw new RuntimeException(e);}
		
		return "/entrada/codigosenha";
	}
		if (professor != null && professor.getEmail().equals(email)){
			professor.setCodigosenha(numero);
			PR.save(professor);
			Properties props = new Properties();
		    /** Parâmetros de conexão com servidor Gmail */
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
		    /** Ativa Debug para sessão */
		    session.setDebug(true);
		    //Enviando email com os dados revertidos.
		    try {
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
		      //Remetente
		      Address[] toUser = InternetAddress //Destinatário(s)
		                 .parse(professor.getEmail());
		      message.setRecipients(Message.RecipientType.TO, toUser);
		      message.setSubject("Recuperação de senha Neduc");//Assunto
		      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + professor.getCodigosenha(); 
		      message.setText(texto);
		      /**Método para enviar a mensagem criada*/
		      Transport.send(message);
		      System.out.println("Mensagem enviada!!!");
		     } catch (MessagingException e) {
		        throw new RuntimeException(e);}
			
		    return "/entrada/codigosenha";
		}
		if(adm != null && adm.getEmail().equals(email)){
			adm.setCodigosenha(numero);
			ADMR.save(adm);
			Properties props = new Properties();
		    /** Parâmetros de conexão com servidor Gmail */
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
		    /** Ativa Debug para sessão */
		    session.setDebug(true);
		    //Enviando email com os dados revertidos.
		    try {
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
		      //Remetente
		      Address[] toUser = InternetAddress //Destinatário(s)
		                 .parse(adm.getEmail());
		      message.setRecipients(Message.RecipientType.TO, toUser);
		      message.setSubject("Recuperação de senha Neduc");//Assunto
		      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + adm.getCodigosenha(); 
		      message.setText(texto);
		      /**Método para enviar a mensagem criada*/
		      Transport.send(message);
		      System.out.println("Mensagem enviada!!!");
		     } catch (MessagingException e) {
		        throw new RuntimeException(e);}
			
		    return "/entrada/codigosenha";
		}
		if(gestor != null && gestor.getEmail().equals(email)){
			gestor.setCodigosenha(numero);
			GR.save(gestor);
			Properties props = new Properties();
		    /** Parâmetros de conexão com servidor Gmail */
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
		    /** Ativa Debug para sessão */
		    session.setDebug(true);
		    //Enviando email com os dados revertidos.
		    try {
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
		      //Remetente
		      Address[] toUser = InternetAddress //Destinatário(s)
		                 .parse(gestor.getEmail());
		      message.setRecipients(Message.RecipientType.TO, toUser);
		      message.setSubject("Recuperação de senha Neduc");//Assunto
		      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + gestor.getCodigosenha(); 
		      message.setText(texto);
		      /**Método para enviar a mensagem criada*/
		      Transport.send(message);
		      System.out.println("Mensagem enviada!!!");
		     } catch (MessagingException e) {
		        throw new RuntimeException(e);}
			
		    return "/entrada/codigosenha";
		}
	return null;
}
	
	
	@RequestMapping("/senhaEmail/{id}")
	public ModelAndView senhaEmail(@PathVariable("id") long id) {
		Alunos aluno = AR.findByid(id);
		ModelAndView mv = new ModelAndView("entrada/testesenha");
		mv.addObject("usuario", aluno);
		return mv;
	}
}
