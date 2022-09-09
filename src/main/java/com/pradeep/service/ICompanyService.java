package com.pradeep.service;

import java.util.List;

import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.exceptions.ResourceNotFoundException;

public interface ICompanyService {

	CompanyUserMapping createCompany(CompanyUserMapping companyusermapping);
	
	List<CompanyUserMappingDto> getCompanyList(Integer companystatus);
	
	ExternalCompany createExternalCompany(ExternalCompany externalCompany, Long companyid) throws ResourceNotFoundException;

	User createUser(User user, Long companyid);

	CompanyDto getCompanyInfoById(Long companyid) throws ResourceNotFoundException;

	User getCompanyUsersById(Long companyid);

	

	
	
}
