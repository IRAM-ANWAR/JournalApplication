package com.journal.schedular;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.journal.scheduler.UserSchedular;

@SpringBootTest
public class UserSchedularTest {

	@Autowired
	UserSchedular userSchedular;

	@Test
	public void testSendEmailForSA() {
		this.userSchedular.fetchUsersAndSendSAMail();
	}

}
