package com.journal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {

	@Autowired
	EmailService emailService;

	@Test
	void testSendEmail() {
		Assertions.assertTrue(this.emailService.sendEmail(null, "Testing Mail from Spring Boot", "KA RE"));
	}

}
