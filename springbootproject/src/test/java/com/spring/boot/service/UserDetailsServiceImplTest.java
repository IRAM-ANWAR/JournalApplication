package com.spring.boot.service;

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

import com.spring.boot.entity.User;
import com.spring.boot.repository.UserRepository;
import com.spring.boot.security.UserDetailsServiceImpl;

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
