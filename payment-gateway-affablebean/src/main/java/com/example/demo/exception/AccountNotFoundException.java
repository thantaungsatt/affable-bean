package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountNotFoundException extends ResponseStatusException{
	
	public AccountNotFoundException(String accountNumber) {
		super(HttpStatus.BAD_REQUEST,accountNumber + " Not Found!");
	}
}
