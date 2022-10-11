package com.pradeep.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserVerificationException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserVerificationException(String message) {
		super(message);
	}

}