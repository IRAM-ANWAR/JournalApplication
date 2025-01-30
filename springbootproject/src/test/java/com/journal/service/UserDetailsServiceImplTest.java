package com.journal.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.journal.entity.User;
import com.journal.repository.UserRepository;

public class UserDetailsServiceImplTest {

	@InjectMocks
	UserDetailsServiceImpl userDetailsService;

	@Mock
	UserRepository userRepository;

	@Test
	void loadUserByUserNameTest() {
		when(this.userRepository.findByUserName(ArgumentMatchers.anyString()))
				.thenReturn(Optional.of(User.builder().userName("Iram").password("Iram").build()));
		UserDetails user = this.userDetailsService.loadUserByUsername("Iram");
		Assertions.assertNotNull(user);
	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

}
