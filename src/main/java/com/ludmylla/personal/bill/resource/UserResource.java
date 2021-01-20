package com.ludmylla.personal.bill.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.model.dto.UserCreateDto;
import com.ludmylla.personal.bill.model.dto.UserLoginDto;
import com.ludmylla.personal.bill.model.dto.UserSearchDto;
import com.ludmylla.personal.bill.service.UserService;

@RestController
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(path = "/user")
	public ResponseEntity<String> createUser(@RequestBody UserCreateDto userCreateDto){
		try {
			 userService.create(userCreateDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(" User created successfully! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create user " + e.getMessage());
		}
	}
	
	@PostMapping(path = "/user/login")
	public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
		try {
			userService.login(userLoginDto);
			return ResponseEntity.status(HttpStatus.OK).body(" Access allowed! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(" Access not allowed. " + e.getMessage());
		}
	}
	
	@GetMapping(path = "/user")
	public ResponseEntity<String> searchUser(UserSearchDto userSearchDto){
		try {
			userService.search(userSearchDto);
			return ResponseEntity.status(HttpStatus.OK).body(" Successfully user found.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(" Failed to search user " + e.getMessage());
		}
	}
	
}
