package com.journal.schedular;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.journal.scheduler.UserSchedular;

@SpringBootTest
class UserSchedularTest {

	@Autowired
	UserSchedular userSchedular;

	@Test
	void testSendEmailForSA() {
		Assertions.assertTrue(this.userSchedular.fetchUsersAndSendSAMail());
	}

}
