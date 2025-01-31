package com.journal.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.journal.entity.User;
import com.journal.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	UserRepository userRepository;

	UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUserName(username).orElse(null);
		if (user != null)
			return org.springframework.security.core.userdetails.User.builder().username(user.getUserName())
			        .password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();

		throw new UsernameNotFoundException("User Not Found with UserName: " + username);
	}

}
