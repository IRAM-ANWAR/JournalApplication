package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.entity.User;
import com.spring.boot.entity.WeatherResponse;
import com.spring.boot.service.UserService;
import com.spring.boot.service.WeatherService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private WeatherService weatherService;

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete() {
		HttpStatus httpStatus = null;
		boolean isDeleted = this.userService.deleteByUserName();
		httpStatus = isDeleted ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(isDeleted, httpStatus);
	}

	@GetMapping("/get")
	public ResponseEntity<?> get() {
		HttpStatus httpStatus = null;
		User user = this.userService.getByUserName();
		httpStatus = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(user, httpStatus);
	}

	@GetMapping("/hello")
	public ResponseEntity<?> getHelloMessage() {
		HttpStatus httpStatus = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		WeatherResponse weatherResponse = this.weatherService.getWeather("MUMBAI");
		String greeting = null;
		if (weatherResponse != null && userName != null)
			greeting = "Hi " + userName + " Weather Feels Like :" + weatherResponse.getCurrent().getFeelslike();
		httpStatus = userName != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(greeting, httpStatus);
	}

	@PutMapping("update")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		HttpStatus httpStatus = null;
		User userUpdated = this.userService.updateEntry(user);
		httpStatus = userUpdated != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(user, httpStatus);
	}

}
