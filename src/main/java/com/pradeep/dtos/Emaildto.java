package com.pradeep.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Emaildto {
	private String [] to;
	private String []  cc;
	private String subject;
	private String body;
	private String templateName;
	private Map<String, Object> templateValues;
}
