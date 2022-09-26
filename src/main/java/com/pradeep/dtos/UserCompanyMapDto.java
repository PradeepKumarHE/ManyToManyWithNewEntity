package com.pradeep.dtos;

import com.pradeep.domain.CompanyAddress;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.ExternalCompanyAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCompanyMapDto {
	private Long companyUserMappingId;	
	private UserDto user;
	private String designation;
	private String role;
	private String [] authorities;
    private boolean isExternal;
    private boolean isActive=true; 
    private CompanyAddress worklocaction;    
	private ExternalCompany externalCompany;    
	private ExternalCompanyAddress externalCompanyAddress;
}
