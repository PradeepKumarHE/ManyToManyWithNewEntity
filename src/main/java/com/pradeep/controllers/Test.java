package com.pradeep.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Test {

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String dateString="2022-09-21T23:05:07.052689";
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
		if(LocalDateTime.now().isBefore(dateTime)) {
			System.out.println("1");
		}else {
			System.out.println("2");
		}

	}

}
