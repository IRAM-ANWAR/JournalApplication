package com.journal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	private JavaMailSender javaMailSender;

	@Value("${spring.mail.to}")
	String toMail;

	@Value("${spring.mail.cc}")
	String ccMail;

	public EmailService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}

	public boolean sendEmail(String to, String subject, String body) {
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setText(body);
			if (to == null)
				mail.setTo(this.toMail);
			else
				mail.setTo(to);
			mail.setSubject(subject);
			mail.setCc(this.ccMail);
			this.javaMailSender.send(mail);
			return true;
		} catch (Exception e) {
			log.error("Exception in sending email", e);
		}
		return false;
	}

}
