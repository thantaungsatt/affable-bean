package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler({AccountNotFoundException.class, InsufficientException.class, SecurityCodeInvalidException.class})
	public ResponseEntity<Object> handleError(Exception ex, WebRequest request) throws Exception{
		return handleExceptionInternal(ex, message(ex), new HttpHeaders(), HttpStatusCode.valueOf(400), request);
	}
	
	private ApiError message(Exception ex) {
		return new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
	}
}
