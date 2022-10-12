package com.pradeep.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CompanyUserMapping extends CustomAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyUserMappingId;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id") 
	@JsonBackReference(value = "company")
	private Company company;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") 
	@JsonBackReference(value = "user")
	private User user;

	private String designation;
	private String role;
	private String [] authorities;
    private Boolean isExternal;
    private Boolean isActive=false;
    
    @OneToOne
    @JoinColumn(name = "worklocation_id") 
	private CompanyAddress worklocation;
    
    @OneToOne
    @JoinColumn(name = "external_company_id") 
	private ExternalCompany externalCompany;
    
    @OneToOne
    @JoinColumn(name = "external_company_address_id") 
	private ExternalCompanyAddress externalCompanyAddress;
}
