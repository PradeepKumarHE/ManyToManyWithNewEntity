package com.pradeep.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationDto {
	private Long userId;
	private String verificationContext;
}
