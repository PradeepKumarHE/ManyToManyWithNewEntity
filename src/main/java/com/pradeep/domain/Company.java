package com.pradeep.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Company {
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
	
	@OneToMany(mappedBy = "company")
	@JsonManagedReference(value = "company")
	List<CompanyUserMapping> companyUserMapping;
	
	@OneToMany(mappedBy = "parentCompany")
	@JsonManagedReference(value = "externalcompanies")
	List<ExternalCompany> externalCompanies;
	
	@OneToMany(mappedBy = "company")
	@JsonManagedReference(value = "companyaddress")
	List<CompanyAddress> companyaddress;
}
