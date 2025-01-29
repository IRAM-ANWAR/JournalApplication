package com.spring.boot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {

	@Autowired
	UserRepositoryImpl userRepositoryImpl;

	@Test
	public void testSaveUser() {
		Assertions.assertNotNull(this.userRepositoryImpl.getUsersForSA());
	}

}
