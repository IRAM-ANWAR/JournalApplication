package com.journal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.to}")
	String toMail;

	@Value("${spring.mail.cc}")
	String ccMail;

	public void sendEmail(String to, String subject, String body) {
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
		} catch (Exception e) {
			log.error("Exception in sending email", e);
		}
	}

}
