package com.pradeep.dtos;

import lombok.Getter;
import lombok.Setter;

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
