package com.pradeep.service;

import com.pradeep.domain.Company;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.exceptions.ResourceNotFoundException;

public interface ICompanyService {

	public CompanyDto getCompanyById(Long companyId) throws ResourceNotFoundException;

}
