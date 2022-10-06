package com.pradeep.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response2 {
	private Integer statusCode;
	private String message;
	private Object data;
}
