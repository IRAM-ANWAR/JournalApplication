package com.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.boot.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SpringSecurity {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(this.userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(request -> request.requestMatchers("/public/**").permitAll()
						.requestMatchers("/journal/**", "/user/**").authenticated().requestMatchers("/admin/**")
						.hasRole("ADMIN").anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).csrf(csrf -> csrf.disable()).build();
	}
}