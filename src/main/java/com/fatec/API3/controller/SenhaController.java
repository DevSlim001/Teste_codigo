package com.fatec.API3.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fatec.API3.model.Administrador;
import com.fatec.API3.model.Alunos;
import com.fatec.API3.model.Gestor;
import com.fatec.API3.model.Professor;
import com.fatec.API3.repository.AdmRepository;
import com.fatec.API3.repository.AlunosRepository;
import com.fatec.API3.repository.GestorRepository;
import com.fatec.API3.repository.ProfessorRepository;

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
		return "/recuperarsenha";
	} 
	@PostMapping("/recuperarsenha")
	public String recuperarsenha(String email){
		Alunos aluno = AR.findByemail(email);
		Professor professor = PR.findByemail(email);
		Gestor gestor = GR.findByemail(email);
		Administrador adm = ADMR.findByemail(email);
		if (aluno == null && professor == null && gestor == null && adm ==null ) {
			return "redirect:index";
		}
		if(aluno != null && aluno.getEmail().equals(email)) {
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
	      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + aluno.getSenha(); 
	      message.setText(texto);
	      /**Método para enviar a mensagem criada*/
	      Transport.send(message);
	      System.out.println("Mensagem enviada!!!");
	     } catch (MessagingException e) {
	        throw new RuntimeException(e);}
		
		return "redirect:login";
	}
		if (professor != null && professor.getEmail().equals(email)){
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
		      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + professor.getSenha(); 
		      message.setText(texto);
		      /**Método para enviar a mensagem criada*/
		      Transport.send(message);
		      System.out.println("Mensagem enviada!!!");
		     } catch (MessagingException e) {
		        throw new RuntimeException(e);}
			
			return "redirect:login";
		}
		if(adm != null && adm.getEmail().equals(email)){
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
		      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + adm.getSenha(); 
		      message.setText(texto);
		      /**Método para enviar a mensagem criada*/
		      Transport.send(message);
		      System.out.println("Mensagem enviada!!!");
		     } catch (MessagingException e) {
		        throw new RuntimeException(e);}
			
			return "redirect:login";
		}
		if(gestor != null && gestor.getEmail().equals(email)){
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
		      String texto = "Olá! não se preocupe, já cuidei disso para você... Sua senha é: " + gestor.getSenha(); 
		      message.setText(texto);
		      /**Método para enviar a mensagem criada*/
		      Transport.send(message);
		      System.out.println("Mensagem enviada!!!");
		     } catch (MessagingException e) {
		        throw new RuntimeException(e);}
			
			return "redirect:login";
		}
	return null;
}
	
}
