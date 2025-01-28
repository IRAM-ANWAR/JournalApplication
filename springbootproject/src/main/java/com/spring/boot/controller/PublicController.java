package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.entity.User;
import com.spring.boot.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> createEntry(@RequestBody User user) {
		HttpStatus httpStatus = null;
		User userSaved = this.userService.saveNewUser(user);
		httpStatus = userSaved != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(userSaved, httpStatus);
	}
}
