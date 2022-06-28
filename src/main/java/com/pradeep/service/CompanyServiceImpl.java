package com.pradeep.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.dtos.CompanyAddressDto;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.dtos.UserAddressDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.ICompanyRepository;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private ICompanyRepository companyRepository;

	@Override
	public CompanyDto getCompanyById(Long companyId) throws ResourceNotFoundException {
		Company existingCompany = companyRepository.findById(companyId).orElseThrow(
				() -> new ResourceNotFoundException("Company not found for the companyId :: " + companyId));
		return getConsolidatedCompanyData(existingCompany);
	}

	public CompanyDto getConsolidatedCompanyData(Company existingCompany) {

		CompanyDto companyDto = new CompanyDto();

		BeanUtils.copyProperties(existingCompany, companyDto);
		CompanyAddressDto companyAddressDto = new CompanyAddressDto();
		
		BeanUtils.copyProperties(existingCompany.getAddress(), companyAddressDto);
		companyDto.setAddress(companyAddressDto);

		Set<CompanyUserMappingDto> companyUserMappingDtoList = new LinkedHashSet<>();
		Set<CompanyUserMapping> companyUserMappingList = existingCompany.getCompanyUserMapping();
		
		for (CompanyUserMapping cum : companyUserMappingList) {
			
			CompanyUserMappingDto companyUserMappingDto = new CompanyUserMappingDto();

			CompanyUserDto companyUserDto = new CompanyUserDto();
			BeanUtils.copyProperties(cum.getUser(), companyUserDto);

			UserAddressDto address = new UserAddressDto();
			BeanUtils.copyProperties(cum.getUser().getAddress(), address);

			companyUserDto.setAddress(address);

			companyUserMappingDto.setUser(companyUserDto);

			BeanUtils.copyProperties(cum, companyUserMappingDto);			
			companyUserMappingDtoList.add(companyUserMappingDto);
		}
		
		companyDto.setAssociatedUsers(companyUserMappingDtoList);

		return companyDto;

	}

}
