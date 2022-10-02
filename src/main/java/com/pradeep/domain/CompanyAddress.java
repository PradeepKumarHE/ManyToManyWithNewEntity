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
public class CompanyAddress  extends CustomAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String street;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;
    private Long zipCode;
    private Integer phoneCountryId;
    private Long phone;
    private Integer phoneExtension; 
    
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "company_id") 
	@JsonBackReference(value = "companyaddress")
	private Company company;
}
