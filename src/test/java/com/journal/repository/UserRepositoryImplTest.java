package com.journal.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryImplTest {

	@Autowired
	UserRepositoryImpl userRepositoryImpl;

	@Test
	void testSaveUser() {
		Assertions.assertNotNull(this.userRepositoryImpl.getUsersForSA());
	}

}
