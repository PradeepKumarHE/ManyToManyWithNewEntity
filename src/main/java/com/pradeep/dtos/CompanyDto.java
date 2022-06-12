package com.pradeep.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto {
	private Long companyId;
	private String companyName;	
	private String companyDescription;
	private String companyWebsite;	
	private Integer companyTypeID;
	private Integer numberOfEmployeesID;
	private String companyLogo;
	private Integer companyStatus;
	private Boolean active;
	private CompanyAddressDto address;
	private Set<CompanyUserMappingDto> associatedUsers;
}
