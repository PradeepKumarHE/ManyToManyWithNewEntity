package com.pradeep.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

public class ResourceUtil {
	
	public static <T> HttpEntity<Object> getNewHttpEntity(T t) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity;
		if (t != null) {
			requestEntity = new HttpEntity<Object>(t, headers);
		} else {
			requestEntity = new HttpEntity<Object>(headers);
		}
		return requestEntity;
	}
}
