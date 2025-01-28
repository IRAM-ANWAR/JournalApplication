package com.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.entity.User;
import com.spring.boot.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@PostMapping("/create/admin")
	public ResponseEntity<?> addAdminUser(@RequestBody User user) {
		HttpStatus httpStatus = null;
		User userSaved = this.userService.saveAdminUser(user);
		httpStatus = userSaved != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(userSaved, httpStatus);
	}

	@GetMapping("/get/users")
	public ResponseEntity<?> getAllUsers() {
		HttpStatus httpStatus = null;
		List<User> users = this.userService.getAllUsers();
		httpStatus = users != null && !users.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(users, httpStatus);
	}
}
