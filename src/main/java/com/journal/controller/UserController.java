package com.journal.controller;

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

import com.journal.dto.UserDto;
import com.journal.entity.WeatherResponse;
import com.journal.service.UserService;
import com.journal.service.WeatherService;
import com.journal.util.GenericMapperUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	private WeatherService weatherService;

	UserController(UserService userService, WeatherService weatherService) {
		this.userService = userService;
		this.weatherService = weatherService;
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> delete() {
		HttpStatus httpStatus = null;
		boolean isDeleted = this.userService.deleteByUserName();
		httpStatus = isDeleted ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(isDeleted, httpStatus);
	}

	@GetMapping("/get")
	public ResponseEntity<UserDto> get() {
		HttpStatus httpStatus = null;
		UserDto user = GenericMapperUtil.mapToDto(this.userService.getByUserName(), UserDto.class);
		httpStatus = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(user, httpStatus);
	}

	@GetMapping("/hello")
	public ResponseEntity<String> getHelloMessage() {
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
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
		HttpStatus httpStatus = null;
		UserDto userUpdated = this.userService.updateEntry(userDto);
		httpStatus = userUpdated != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(userDto, httpStatus);
	}

}
