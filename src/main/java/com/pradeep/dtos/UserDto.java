package com.pradeep.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
	private Long userId;
	private String email;
	Set<UserCompanyMappingDto> associatedCompanies;
}
