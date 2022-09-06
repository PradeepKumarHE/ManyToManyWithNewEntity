package com.pradeep.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class UserAddressDto {
    private Long addressId;
    private String street;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;
    private Long zipCode;
    private Integer phoneCountryId;
    private Long phone;
    private Integer landPhoneCountryId;
    private Long landPhone;
    private Integer landPhoneExtension;
}
