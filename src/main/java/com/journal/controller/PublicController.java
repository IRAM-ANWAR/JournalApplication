package com.journal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.dto.UserDto;
import com.journal.service.UserService;
import com.journal.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

	private UserService userService;
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;
	private JWTUtil jwtUtil;

	PublicController(UserService userService, AuthenticationManager authenticationManager,
	        UserDetailsService userDetailsService, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/sign-up")
	public ResponseEntity<UserDto> createEntry(@RequestBody UserDto userDto) {
		HttpStatus httpStatus = null;
		UserDto userSaved = this.userService.saveNewUser(userDto);
		httpStatus = userSaved != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(userSaved, httpStatus);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto userDto) {
		try {
			this.authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDto.getUserName());
			String jwt = this.jwtUtil.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Login Unsuccessfull", e);
			return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
		}
	}
}
