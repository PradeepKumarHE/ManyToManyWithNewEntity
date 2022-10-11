package com.pradeep.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMobileDto {
	private String firstname;
	private String lastname;
	private Integer phoneCountryId;
	private Long phone;
	private String tinystring;
	private Boolean isRegsitrationFlow;
}
