package com.pradeep.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto {
	private Long companyId;
	private String companyName;	
	Set<CompanyUserMappingDto> associatedUsers;
}
