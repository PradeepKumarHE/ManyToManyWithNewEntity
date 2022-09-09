package com.pradeep.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExternalCompanyAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long externalCompanyAddressId;
	private String street;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	private Long zipCode;
	private Integer phoneCountryId;
	private Long phone;
	private Integer phoneExtension;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "external_company_id") 
	private ExternalCompany externalcompany;
}
