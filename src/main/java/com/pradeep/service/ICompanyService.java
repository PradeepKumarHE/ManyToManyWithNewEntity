package com.pradeep.service;

import java.util.List;

import com.pradeep.domain.CompanyAddress;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.dtos.UserCompanyMapDto;
import com.pradeep.exceptions.ResourceExistsException;
import com.pradeep.exceptions.ResourceNotFoundException;

public interface ICompanyService {

	CompanyUserMapping createCompany(CompanyUserMapping companyusermapping) throws ResourceExistsException;
	
	List<CompanyUserMappingDto> getCompanyListByStatus(Integer companystatus);
	
	ExternalCompany createExternalCompany(ExternalCompany externalCompany, Long companyid) throws ResourceNotFoundException, ResourceExistsException;

	CompanyUserMapping createUser(CompanyUserMapping companyUserMapping, Long companyid) throws ResourceNotFoundException, ResourceExistsException;

	CompanyDto getCompanyInfoById(Long companyid) throws ResourceNotFoundException;

	List<UserCompanyMapDto> getCompanyUsersById(Long companyid);

	CompanyAddress addCompanyAddress(CompanyAddress companyAddress, Long companyid) throws ResourceNotFoundException;

	CompanyUserMapping updateCompany(Long companyUserMappingId, CompanyUserMapping companyusermapping) throws ResourceExistsException, ResourceNotFoundException;


	

	
	
}
