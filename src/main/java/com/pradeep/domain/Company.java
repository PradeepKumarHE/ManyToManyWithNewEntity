package com.pradeep.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
	@OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
	Set<CompanyUserMapping> companyUserMapping;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private CompanyAddress address;
}
