package com.pradeep.service;

import java.util.List;

import com.pradeep.domain.CompanyAddress;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.dtos.UserCompanyMapDto;
import com.pradeep.exceptions.ResourceNotFoundException;

public interface ICompanyService {

	CompanyUserMapping createCompany(CompanyUserMapping companyusermapping);
	
	List<CompanyUserMappingDto> getCompanyListByStatus(Integer companystatus);
	
	ExternalCompany createExternalCompany(ExternalCompany externalCompany, Long companyid) throws ResourceNotFoundException;

	CompanyUserMapping createUser(CompanyUserMapping companyUserMapping, Long companyid) throws ResourceNotFoundException;

	CompanyDto getCompanyInfoById(Long companyid) throws ResourceNotFoundException;

	List<UserCompanyMapDto> getCompanyUsersById(Long companyid);

	CompanyAddress addCompanyAddress(CompanyAddress companyAddress, Long companyid) throws ResourceNotFoundException;

	

	
	
}
