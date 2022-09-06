package com.pradeep.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyUserMappingDto {
	private Long companyUserMappingId;	
	private CompanyDto company;	
	private UserDto user;
	private String designation;
	private String role;
	private String [] authorities;
    private boolean isExternal;
    private boolean isActive=true;    
}
