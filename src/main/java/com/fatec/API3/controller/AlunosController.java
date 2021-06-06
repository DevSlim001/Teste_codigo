package com.fatec.API3.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

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
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.API3.model.Alunos;
import com.fatec.API3.repository.AlunosRepository;

@Controller
@RequestMapping("/")
public class AlunosController {
	
	
	
	
	private static String caminhoimagens = "C:\\Users\\kiabi\\OneDrive\\Documentos\\Imagens_API3";
	//private static String caminhoimagens = "C:\\Users\\Pichau\\Documents\\Imagens_API3\\";
	
	@Autowired
	private AlunosRepository AR;
	
	
	
	@GetMapping("/homealuno")
	public String homealuno(){
		return "/aluno/homealuno"; 
}
	
	@GetMapping("/cadastroaluno")
	public String cadastroaluno(){
		return "/entrada/cadastroaluno";
	}
	
	@PostMapping("/cadastroaluno")
	//Verificando se o email já está cadastrado.
	public String cadastroalunop(Alunos alunos){
		boolean existe = AR.existsByemail(alunos.getEmail());
		if(existe) {return "Usuário já cadastrado na plataforma.";}
		String senha = alunos.getSenha();
		String senhaenc = new BCryptPasswordEncoder().encode(senha);
		alunos.setSenha(senhaenc);
	//Colocando propriedades para o envio de email.
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
		      attachment0.setContent(email1,"text/html; charset=UTF-8");
		      multipart.addBodyPart(attachment0);
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress("timeslim123@gmail.com"));
		      //Remetente
		      Address[] toUser = InternetAddress //Destinatário(s)
		                 .parse(alunos.getEmail());
		      message.setRecipients(Message.RecipientType.TO, toUser);
		      message.setSubject("Confirmação de cadastro - nEDUC");//Assunto
		      message.setContent(multipart);
		      /**Método para enviar a mensagem criada*/
		      Transport.send(message);

	      System.out.println("Mensagem enviada!!!");

	     } catch (MessagingException e) {
	        throw new RuntimeException(e);}
		AR.save(alunos);
		return "redirect:login";
	}
	
	
	
	@GetMapping("/tarefas")
	public String tarefasaluno(){
		return "/tarefas";
	}
	
	@PostMapping("/tarefas") 
	public String uploadtarefas(Alunos alunos, @RequestParam("file") MultipartFile arquivo) throws IOException {
		if (!arquivo.isEmpty()) {
			byte[] bytes = arquivo.getBytes();
			Path caminho = Paths
					.get(caminhoimagens + String.valueOf(alunos.getId()) + arquivo.getOriginalFilename());
			Files.write(caminho, bytes);

			alunos.setNomearquivo(String.valueOf(alunos.getId()) + arquivo.getOriginalFilename());
			AR.save(alunos);
		}
		return"redirect:homealuno";
	
}
	
	String email1 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
			+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n"
			+ "<head>\r\n"
			+ "<meta charset=\"UTF-8\">\r\n"
			+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\r\n"
			+ "<meta name=\"x-apple-disable-message-reformatting\">\r\n"
			+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "<meta content=\"telephone=no\" name=\"format-detection\">\r\n"
			+ "<title>nEDUC</title>\r\n"
			+ "<!--[if (mso 16)]>\r\n"
			+ "<style type=\"text/css\">\r\n"
			+ "a {text-decoration: none;}\r\n"
			+ "</style>\r\n"
			+ "<![endif]-->\r\n"
			+ "<!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->\r\n"
			+ "<!--[if gte mso 9]>\r\n"
			+ "<xml>\r\n"
			+ "<o:OfficeDocumentSettings>\r\n"
			+ "<o:AllowPNG></o:AllowPNG>\r\n"
			+ "<o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
			+ "</o:OfficeDocumentSettings>\r\n"
			+ "</xml>\r\n"
			+ "<![endif]-->\r\n"
			+ "<style type=\"text/css\">\r\n"
			+ "#outlook a {\r\n"
			+ "padding:0;\r\n"
			+ "}\r\n"
			+ ".ExternalClass {\r\n"
			+ "width:100%;\r\n"
			+ "}\r\n"
			+ ".ExternalClass,\r\n"
			+ ".ExternalClass p,\r\n"
			+ ".ExternalClass span,\r\n"
			+ ".ExternalClass font,\r\n"
			+ ".ExternalClass td,\r\n"
			+ ".ExternalClass div {\r\n"
			+ "line-height:100%;\r\n"
			+ "}\r\n"
			+ ".es-button {\r\n"
			+ "mso-style-priority:100!important;\r\n"
			+ "text-decoration:none!important;\r\n"
			+ "}\r\n"
			+ "a[x-apple-data-detectors] {\r\n"
			+ "color:inherit!important;\r\n"
			+ "text-decoration:none!important;\r\n"
			+ "font-size:inherit!important;\r\n"
			+ "font-family:inherit!important;\r\n"
			+ "font-weight:inherit!important;\r\n"
			+ "line-height:inherit!important;\r\n"
			+ "}\r\n"
			+ ".es-desk-hidden {\r\n"
			+ "display:none;\r\n"
			+ "float:left;\r\n"
			+ "overflow:hidden;\r\n"
			+ "width:0;\r\n"
			+ "max-height:0;\r\n"
			+ "line-height:0;\r\n"
			+ "mso-hide:all;\r\n"
			+ "}\r\n"
			+ "[data-ogsb] .es-button {\r\n"
			+ "border-width:0!important;\r\n"
			+ "padding:10px 20px 10px 20px!important;\r\n"
			+ "}\r\n"
			+ "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1 { font-size:30px!important; text-align:left; line-height:120%!important } h2 { font-size:26px!important; text-align:left; line-height:120%!important } h3 { font-size:20px!important; text-align:left; line-height:120%!important } h1 a { text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important } h3 a { text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button, button.es-button { font-size:20px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }\r\n"
			+ "</style>\r\n"
			+ "</head>\r\n"
			+ "<body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n"
			+ "<div class=\"es-wrapper-color\" style=\"background-color:#EFEFEF\">\r\n"
			+ "<!--[if gte mso 9]>\r\n"
			+ "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n"
			+ "<v:fill type=\"tile\" color=\"#efefef\"></v:fill>\r\n"
			+ "</v:background>\r\n"
			+ "<![endif]-->\r\n"
			+ "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
			+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0\">\r\n"
			+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" bgcolor=\"#6fa8dc\" style=\"padding:10px;Margin:0;background-color:#6FA8DC\">\r\n"
			+ "<!--[if mso]><table style=\"width:580px\"><tr><td style=\"width:280px\" valign=\"top\"><![endif]-->\r\n"
			+ "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" style=\"padding:0;Margin:0;width:280px\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td class=\"es-infoblock es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:17px;color:#000000;font-size:14px\"><b>Confirmação de cadastro</b></p></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table>\r\n"
			+ "<!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:280px\" valign=\"top\"><![endif]-->\r\n"
			+ "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" style=\"padding:0;Margin:0;width:280px\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\"><br></p></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table>\r\n"
			+ "<!--[if mso]></td></tr></table><![endif]--></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table>\r\n"
			+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
			+ "<table class=\"es-header-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#E6EBEF;width:600px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" bgcolor=\"#cfe2f3\" style=\"padding:20px;Margin:0;background-color:#CFE2F3\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:72px;color:#333333;font-size:48px\"><strong>nEDUC</strong></p></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table>\r\n"
			+ "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
			+ "<table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" style=\"Margin:0;padding-left:30px;padding-right:30px;padding-top:40px;padding-bottom:40px\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:540px\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" style=\"padding:0;Margin:0\"><h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#3D85C6\">Olá estudante, esse email foi enviado para te notificar da sua inscrição em nossa plataforma</h3></td>\r\n"
			+ "</tr>\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-top:15px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">Você se cadastrou na plataforma nEDUC. não se esqueça de ficar por dentro das novidades&nbsp;<br><br></p></td>\r\n"
			+ "</tr>\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"center\" style=\"padding:0;Margin:0\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:transparent;background:#6FA8DC;border-width:0px;display:inline-block;border-radius:1px;width:auto\"><a href=\"http://localhost:9876/index\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#FFFFFF;font-size:16px;border-style:solid;border-color:#6FA8DC;border-width:10px 20px 10px 20px;display:inline-block;background:#6FA8DC;border-radius:1px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:19px;width:auto;text-align:center;border-left-width:20px;border-right-width:20px\">Iniciar os Estudos</a></span></td>\r\n"
			+ "</tr>\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-top:15px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\"><br>Equipe nEDUC</p></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table>\r\n"
			+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
			+ "<table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#E6EBEF;width:600px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"left\" bgcolor=\"#cfe2f3\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;background-color:#CFE2F3\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\r\n"
			+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;font-size:0\">\r\n"
			+ "<table class=\"es-table-not-adapt es-social\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><img title=\"Facebook\" src=\"https://imagepng.org/wp-content/uploads/2017/09/facebook-icone-icon.png\" alt=\"Fb\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\r\n"
			+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><img title=\"Twitter\" src=\"https://logodownload.org/wp-content/uploads/2014/09/twitter-logo-6.png\" alt=\"Tw\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\r\n"
			+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><img title=\"Instagram\" src=\"https://imagepng.org/wp-content/uploads/2017/08/instagram-icone-icon-1.png\" alt=\"Inst\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\r\n"
			+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><img title=\"Youtube\" src=\"https://image.flaticon.com/icons/png/512/1384/1384060.png\" alt=\"Yt\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "<tr style=\"border-collapse:collapse\">\r\n"
			+ "<td align=\"center\" style=\"Margin:0;padding-bottom:10px;padding-top:15px;padding-left:15px;padding-right:15px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:20px;color:#999999;font-size:13px\">© 2021 nEDUC, Todos os direitos reservados</p></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table></td>\r\n"
			+ "</tr>\r\n"
			+ "</table>\r\n"
			+ "</div>\r\n"
			+ "</body>\r\n"
			+ "</html>";
}

