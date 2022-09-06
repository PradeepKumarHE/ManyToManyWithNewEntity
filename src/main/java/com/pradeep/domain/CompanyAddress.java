package com.pradeep.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CompanyAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String street;
    private Integer countryId;
    private Integer stateID;
    private Integer cityID;
    private Long zipCode;
    private Integer phoneCountryID;
    private Long phone;
    private Integer phoneExtension;   
}
