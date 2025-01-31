package com.journal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.journal.filter.JWTFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	private JWTFilter jwtFilter;

	public SpringSecurity(JWTFilter jwtFilter) {
		super();
		this.jwtFilter = jwtFilter;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
		        .authorizeHttpRequests(request -> request.requestMatchers("/public/**").permitAll()
		                .requestMatchers("/swagger-ui/**").permitAll().requestMatchers("/v3/api-docs*/**").permitAll()
		                .requestMatchers("/journal/**", "/user/**").authenticated().requestMatchers("/admin/**")
		                .hasRole("ADMIN").anyRequest().authenticated())
		        // .httpBasic(Customizer.withDefaults())//Removing this to enable jwt token
		        // authentication, this is part of spring security basic authentication
		        // csrf.disable
		        .csrf(AbstractHttpConfigurer::disable)
		        .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

}