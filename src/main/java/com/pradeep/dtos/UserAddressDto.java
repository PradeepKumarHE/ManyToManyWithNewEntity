package com.pradeep.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressDto {
	private Long addressId;
	private String street;
	private Integer countryId;
	private Integer stateID;
	private Integer cityID;
	private Long zipCode;
	private Integer phoneCountryID;
	private Long phone;
	private Integer landPhoneCountryID;
	private Long landPhone;
	private Integer landPhoneExtension;

}
