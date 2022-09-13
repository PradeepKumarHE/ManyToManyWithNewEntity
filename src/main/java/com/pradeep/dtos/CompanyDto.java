package com.pradeep.dtos;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.pradeep.domain.CompanyAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	//Set<ExternalCompany> externalCompanies;	
	private List<CompanyAddress> companyaddress;
}
