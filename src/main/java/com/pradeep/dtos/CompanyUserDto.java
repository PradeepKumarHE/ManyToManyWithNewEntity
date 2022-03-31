package com.pradeep.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyUserDto {
	private Long userId;
	private String email;
}
