package com.pradeep.service;

import java.util.List;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.dtos.CompanyUserMappingDto;

public interface ICompanyUserMappingService {

	CompanyUserMapping createCompany(CompanyUserMapping companyusermapping);

	List<CompanyUserMappingDto> getCompanyList(Integer companystatus);

	
}
