package com.pradeep.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExternalCompanyAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long externalCompanyAddressId;
	private String ec_street;
	private Integer ec_countryId;
	private Integer ec_stateId;
	private Integer ec_cityId;
	private Long ec_zipCode;
	private Integer ec_phoneCountryId;
	private Long ec_phone;
	private Integer ec_phoneExtension;
	
	@ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "external_company_id") 
	@JsonBackReference(value = "externalcompanyaddresses")
	private ExternalCompany externalcompany;
}
