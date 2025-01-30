package com.journal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

	@Autowired
	EmailService emailService;

	@Test
	public void testSendEmail() {
		this.emailService.sendEmail(null, "Testing Mail from Spring Boot", "KA RE");
	}

}
