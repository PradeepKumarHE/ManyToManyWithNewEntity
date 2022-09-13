package com.pradeep.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExternalCompany {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long externalCompanyId;
	private String companyName;
	private String companyDescription;
	private String companyWebsite;
	private Integer companyTypeID;
	private Integer numberOfEmployeesID;
	private String companyLogo;	
	private Boolean active=Boolean.TRUE;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_company_id") 
	@JsonBackReference(value = "externalcompanies")
	private Company parentCompany;
	
	@OneToMany(mappedBy = "externalcompany")
	@JsonManagedReference(value = "externalcompanyaddresses")
	private List<ExternalCompanyAddress> externalcompanyaddresses;
}
