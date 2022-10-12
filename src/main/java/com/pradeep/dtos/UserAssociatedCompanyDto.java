package com.pradeep.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAssociatedCompanyDto {
    private Long companyId;
    private String companyName;
    private String designation;
    private String role;
    private String [] authorities;
    private Boolean isExternal;
}
