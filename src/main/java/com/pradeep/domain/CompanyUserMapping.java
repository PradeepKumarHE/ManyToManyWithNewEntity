package com.pradeep.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class 	CompanyUserMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyUserMappingId;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id") 
	private Company company;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") 
	private User user;

	private String role;
	private String [] authorities;
    private boolean isExternal;
    private boolean isActive;
	private String companyName;
	private String companyType;
}
