package com.journal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.cache.AppCache;
import com.journal.dto.UserDto;
import com.journal.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private UserService userService;

	private AppCache appCache;

	AdminController(UserService userService, AppCache appCache) {
		this.userService = userService;
		this.appCache = appCache;
	}

	@PostMapping("/create/adminuser")
	public ResponseEntity<UserDto> addAdminUser(@RequestBody UserDto userDto) {
		HttpStatus httpStatus = null;
		UserDto userSaved = this.userService.saveAdminUser(userDto);
		httpStatus = userSaved != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(userSaved, httpStatus);
	}

	@GetMapping("/clear-app-cache")
	public ResponseEntity<String> clearAppCache() {
		this.appCache.init();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/get/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		HttpStatus httpStatus = null;
		List<UserDto> users = this.userService.getAllUsers();
		httpStatus = users != null && !users.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(users, httpStatus);
	}

}
