package com.journal.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.journal.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("dev")
class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserService userService;

	@ParameterizedTest
	@CsvSource({ "Ram,Iram" })
	void testFindByUserName(String userName) {
		assertNotNull(this.userRepository.findByUserName(userName), "Failed for " + userName);
	}

	@ParameterizedTest
	@ValueSource(strings = { "Ram", "Iram" })
	void testFindByUserName2(String userName) {
		assertNotNull(this.userRepository.findByUserName(userName), "Failed for " + userName);
	}

//	@ParameterizedTest
//	@ArgumentsSource(UserArguementsProvider.class)
//	public void testSaveUser(User user) {
//		this.userService.saveNewUser(user);
//		assertNotNull(this.userService.saveNewUser(user));
//	}

}
