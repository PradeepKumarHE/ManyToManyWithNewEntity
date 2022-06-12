package com.pradeep.service;

import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;
import com.pradeep.dtos.AddUserRequestDTo;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.exceptions.UserAssociatedToCompanyException;

import java.util.List;

public interface ICompanyUserMappingService {

	public CompanyUserMapping createCompany(CompanyUserMapping companyusermapping);

	public CompanyUserMapping addUserToExistingCompany(Long companyId, AddUserRequestDTo addUserRequestDTo) throws ResourceNotFoundException, UserAssociatedToCompanyException;

    List<CompanyDto> listPotentialCompanies();
}
