package com.pradeep.service;

import java.util.List;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;

public interface ICompanyUserMappingService {

	CompanyUserMapping createCompany(CompanyUserMapping companyusermapping);

	List<CompanyUserMapping> getCompanyList(Integer companystatus);

	
}
