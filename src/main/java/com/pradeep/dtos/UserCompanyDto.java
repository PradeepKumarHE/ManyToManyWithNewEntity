package com.pradeep.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserCompanyDto {
	private Long companyId;
	private String companyName;	
	private String companyDescription;
	private String companyWebsite;
	private String companyEmailDomain;
	private Integer companyTypeID;
	private Integer numberOfEmployeesID;
	private String companyLogo;
	private Integer companyStatus;
	private Boolean active;
	private CompanyAddressDto address;
}
