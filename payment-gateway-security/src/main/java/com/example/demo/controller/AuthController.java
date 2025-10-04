package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	record RegisterRequest(String username, String password, String email) {}
	record LoginRequest(String username, String password) {}
	record AccessRequest(String username, String code) {}

	@PostMapping("/access")
	public ResponseEntity<Void> validateOtp(@RequestBody AccessRequest request) {
		if (authService.validateOtp(request.username, request.code)) {
			return ResponseEntity.status(HttpStatus.OK).build();			
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request){
		String responseString=authService.register(request.username, request.password, request.email);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseString);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String>login(@RequestBody LoginRequest request){
		String responseString=authService.login(request.username, request.password);
		return ResponseEntity.ok(responseString);
		
	}
}
