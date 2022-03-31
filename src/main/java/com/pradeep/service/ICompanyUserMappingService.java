package com.pradeep.service;

import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.exceptions.UserAssociatedToCompanyException;

public interface ICompanyUserMappingService {

	public CompanyUserMapping createCompany(CompanyUserMapping companyusermapping);

	public CompanyUserMapping addUserToExistingCompany(Long companyId, User user) throws ResourceNotFoundException, UserAssociatedToCompanyException;

}
