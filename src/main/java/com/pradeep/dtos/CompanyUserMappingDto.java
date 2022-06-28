package com.pradeep.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompanyUserMappingDto {
	private Long companyUserMappingId;
	private CompanyUserDto user;
	private boolean isExternal;
    private boolean isActive;
    private String role;
	private String [] authorities;
}