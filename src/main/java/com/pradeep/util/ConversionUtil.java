package com.pradeep.util;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConversionUtil {

	public static String flattenUserName(String primaryEmail) {
		return primaryEmail.replace(".", "").replace("@", "");
	}
	
	public static String getEmailDomain(String email) {
		return email.substring(email.indexOf('@')+1,email.length()).replace(".", "");
	}
	
}
