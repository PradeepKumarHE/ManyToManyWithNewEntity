package com.pradeep.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response1 {
	private Integer statusCode;
	private String message;
}
