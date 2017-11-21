package org.paquete.base.utm.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class RootContextConfig {

	@Bean
	public JavaMailSender javaMailSender() {

		/**
		 *  Utiliza Gmail como servidor de correo para enviar
		 *  email en el servicio NotificationServiceImpl que consume 
		 *  el bean mailSender. Agregar usuario y password de una cuenta
		 *  genérica
		 * */
 	    
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.mail.yahoo.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("taniamontemayor");
	    mailSender.setPassword("asdfasdfsdaf");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;

	}
}
