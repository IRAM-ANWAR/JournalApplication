package com.journal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.cache.AppCache;
import com.journal.entity.User;
import com.journal.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	AppCache appCache;

	@PostMapping("/create/admin")
	public ResponseEntity<?> addAdminUser(@RequestBody User user) {
		HttpStatus httpStatus = null;
		User userSaved = this.userService.saveAdminUser(user);
		httpStatus = userSaved != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(userSaved, httpStatus);
	}

	@GetMapping("/clear-app-cache")
	public ResponseEntity<?> clearAppCache() {
		this.appCache.init();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/get/users")
	public ResponseEntity<?> getAllUsers() {
		HttpStatus httpStatus = null;
		List<User> users = this.userService.getAllUsers();
		httpStatus = users != null && !users.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(users, httpStatus);
	}

}
