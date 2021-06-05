package com.fatec.API3.controller;

import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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

@Controller
public class SenhaController {
	
	String email1 = "<html>"
			+"<meta name='viewport' content='width=device-width, initial scale=1.0'>"
    		+"<link rel='preconnect' href='https://fonts.gstatic.com'>"
    		+"<link href='https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700;900&display=swap' rel='stylesheet'>"
    		+"<link rel='stylesheet' href='../css/main.css' />"
			+"<div id=\"header\">\r\n"
			+ "        <div class=\"container\">\r\n"
			+ "            <nav class=\"navbar navbar-expand-lg navbar-light justify-content-between\">\r\n"
			+ "                    <a class=\"navbar-brand\" href=\"https://www.neduc/index\">\r\n"
			+ "                        <img src=\"https://media-exp1.licdn.com/dms/image/C4D0BAQHQawKedR0znA/company-logo_200_200/0/1585075325806?e=2159024400&v=beta&t=CWVL261R-J4ILmFV_75Q5yxJSoG7fiBgk7jhmcBciII\" width=\"150px\" class=\"img-fluid\" />\r\n"
			+ "                    </a>"
			+ "<meta charset='UTF-8'> <h1>Recuperação de Senha Neduc</h1> "
			+ "<h3>Olá! não se preocupe, já cuidei disso para você</h3> "
			+ "<h3>Seu código de recuperação de senha é: ";
	
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
			String senhaenc = new BCryptPasswordEncoder().encode(senha);
			aluno.setSenha(senhaenc);
			AR.save(aluno);
			return "/login";
		}
		
		if (professor != null && professor.getCodigosenha().equals(codigosenha) && professor.getEmail().equals(email)) {
			String senhaenc = new BCryptPasswordEncoder().encode(senha);
			professor.setSenha(senhaenc);
			PR.save(professor);
			return "/login";
		}
		
		if (adm != null && adm.getCodigosenha().equals(codigosenha) && adm.getEmail().equals(email)) {
			String senhaenc = new BCryptPasswordEncoder().encode(senha);
			adm.setSenha(senhaenc);
			ADMR.save(adm);
			return "/login";
		}
		
		if (gestor != null && gestor.getCodigosenha().equals(codigosenha) && gestor.getEmail().equals(email)) {
			String senhaenc = new BCryptPasswordEncoder().encode(senha);
			gestor.setSenha(senhaenc);
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
	      Multipart multipart = new MimeMultipart();
	      MimeBodyPart attachment0 = new MimeBodyPart();
	      attachment0.setContent(email1 + aluno.getCodigosenha(),"text/html; charset=UTF-8");
	      multipart.addBodyPart(attachment0);
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
	      //Remetente
	      Address[] toUser = InternetAddress //Destinatário(s)
	                 .parse(aluno.getEmail());
	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject("Recuperação de senha Neduc");//Assunto
	      message.setContent(multipart);
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
		    	Multipart multipart = new MimeMultipart();
			      MimeBodyPart attachment0 = new MimeBodyPart();
			      attachment0.setContent(email1 + professor.getCodigosenha(),"text/html; charset=UTF-8");
			      multipart.addBodyPart(attachment0);
			      Message message = new MimeMessage(session);
			      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
			      //Remetente
			      Address[] toUser = InternetAddress //Destinatário(s)
			                 .parse(aluno.getEmail());
			      message.setRecipients(Message.RecipientType.TO, toUser);
			      message.setSubject("Recuperação de senha Neduc");//Assunto
			      message.setContent(multipart);
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
		    	Multipart multipart = new MimeMultipart();
			      MimeBodyPart attachment0 = new MimeBodyPart();
			      attachment0.setContent(email1 + adm.getCodigosenha(),"text/html; charset=UTF-8");
			      multipart.addBodyPart(attachment0);
			      Message message = new MimeMessage(session);
			      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
			      //Remetente
			      Address[] toUser = InternetAddress //Destinatário(s)
			                 .parse(aluno.getEmail());
			      message.setRecipients(Message.RecipientType.TO, toUser);
			      message.setSubject("Recuperação de senha Neduc");//Assunto
			      message.setContent(multipart);
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
		    	Multipart multipart = new MimeMultipart();
			      MimeBodyPart attachment0 = new MimeBodyPart();
			      attachment0.setContent(email1 + gestor.getCodigosenha(),"text/html; charset=UTF-8");
			      multipart.addBodyPart(attachment0);
			      Message message = new MimeMessage(session);
			      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
			      //Remetente
			      Address[] toUser = InternetAddress //Destinatário(s)
			                 .parse(aluno.getEmail());
			      message.setRecipients(Message.RecipientType.TO, toUser);
			      message.setSubject("Recuperação de senha Neduc");//Assunto
			      message.setContent(multipart);
			      /**Método para enviar a mensagem criada*/
			      Transport.send(message);
		      System.out.println("Mensagem enviada!!!");
		     } catch (MessagingException e) {
		        throw new RuntimeException(e);}
			
		    return "/entrada/codigosenha";
		}
	return null;
}
	
}
