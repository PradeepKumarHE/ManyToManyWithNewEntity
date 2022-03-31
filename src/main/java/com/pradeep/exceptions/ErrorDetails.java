package com.pradeep.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String path;
	private String application;
}