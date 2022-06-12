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
	private String firstName;
	private String lastName;
	private String email;
	private String userStatus;
	private UserAddressDto address;
	Set<UserCompanyMappingDto> associatedCompanies;
}
