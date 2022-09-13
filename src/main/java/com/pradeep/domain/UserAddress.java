package com.pradeep.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long useraddressId;
    private String user_street;
    private Integer user_countryId;
    private Integer user_stateId;
    private Integer user_cityId;
    private Long user_zipCode;
    private Integer user_phoneCountryId;
    private Long user_phone;
    private Integer user_landPhoneCountryId;
    private Long user_landPhone;
    private Integer user_landPhoneExtension;
}
