package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InsufficientException extends ResponseStatusException{
	public InsufficientException() {
		super(HttpStatus.BAD_REQUEST,"Amount is insufficient!");
	}

}
