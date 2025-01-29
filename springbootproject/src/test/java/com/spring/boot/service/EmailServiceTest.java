package com.spring.boot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

	@Autowired
	EmailService emailService;

	@Test
	public void testSendEmail() {
		this.emailService.sendEmail("###########", "#################", "Testing Mail from Spring Boot",
		        "------------------------------------------------------------------------------------------");
	}

}
