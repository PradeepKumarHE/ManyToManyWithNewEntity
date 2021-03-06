package com.pradeep.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyAddressDto {
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
