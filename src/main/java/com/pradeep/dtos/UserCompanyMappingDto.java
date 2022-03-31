package com.pradeep.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserCompanyMappingDto {
	private Long companyUserMappingId;
	private UserCompanyDto company;
	private boolean isExternal;
    private boolean isActive;
}
