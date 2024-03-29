package com.pradeep.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAssociatedToCompanyException extends Exception{

	private static final long serialVersionUID = -1628332750546948830L;

	public UserAssociatedToCompanyException(String message) {
		super(message);
	}
}

