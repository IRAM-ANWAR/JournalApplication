package com.spring.boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spring.boot.entity.User;
import com.spring.boot.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userRepository.findByUserName(username).orElse(null);
		if (user != null)
			return org.springframework.security.core.userdetails.User.builder().username(user.getUserName())
					.password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();

		throw new UsernameNotFoundException("User Not Found with UserName: " + username);
	}

}
