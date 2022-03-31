package com.pradeep.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.ICompanyRepository;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private ICompanyRepository companyRepository;
	
	@Override
	public CompanyDto getCompanyById(Long companyId) throws ResourceNotFoundException {
		Company existingCompany = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company not found for the companyId :: "+companyId));
		return getConsolidatedCompanyData(existingCompany);
	}
	
	public CompanyDto getConsolidatedCompanyData(Company existingCompany) {
		CompanyDto companyDto=new CompanyDto();
		companyDto.setCompanyId(existingCompany.getCompanyId());
		companyDto.setCompanyName(existingCompany.getCompanyName());
		
		Set<CompanyUserMappingDto> companyUserMappingDtoList=new LinkedHashSet<>();
		
		Set<CompanyUserMapping> companyUserMappingList=existingCompany.getCompanyUserMapping();
		for (CompanyUserMapping cum : companyUserMappingList) {
			CompanyUserMappingDto companyUserMappingDto=new CompanyUserMappingDto();
			companyUserMappingDto.setCompanyUserMappingId(cum.getCompanyUserMappingId());
			CompanyUserDto cdto=new CompanyUserDto();
			cdto.setUserId(cum.getUser().getUserId());
			cdto.setEmail(cum.getUser().getEmail());
			companyUserMappingDto.setUser(cdto);
			companyUserMappingDto.setActive(cum.isActive());
			companyUserMappingDto.setExternal(cum.isExternal());
			companyUserMappingDtoList.add(companyUserMappingDto);
		}
		companyDto.setAssociatedUsers(companyUserMappingDtoList);
		return companyDto;
		
	}

}
