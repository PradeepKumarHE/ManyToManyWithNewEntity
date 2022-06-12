package com.pradeep.service;

import java.util.LinkedHashSet;
import java.util.Set;

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
		Company existingCompany = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company not found for the companyId :: "+companyId));
		return getConsolidatedCompanyData(existingCompany);
	}
	
	public CompanyDto getConsolidatedCompanyData(Company existingCompany) {
		CompanyDto companyDto=new CompanyDto();		
		companyDto.setCompanyId(existingCompany.getCompanyId());
		companyDto.setCompanyName(existingCompany.getCompanyName());
		companyDto.setCompanyDescription(existingCompany.getCompanyDescription());
		companyDto.setCompanyWebsite(existingCompany.getCompanyWebsite());
		companyDto.setCompanyTypeID(existingCompany.getCompanyTypeID());
		companyDto.setNumberOfEmployeesID(existingCompany.getNumberOfEmployeesID());
		companyDto.setCompanyLogo(existingCompany.getCompanyLogo());
		companyDto.setActive(existingCompany.getActive());
		
		CompanyAddressDto companyAddressDto=new CompanyAddressDto();
		companyAddressDto.setAddressId(existingCompany.getAddress().getAddressId());
		companyAddressDto.setStreet(existingCompany.getAddress().getStreet());
		companyAddressDto.setCountryId(existingCompany.getAddress().getCountryId());
		companyAddressDto.setStateID(existingCompany.getAddress().getStateID());
		companyAddressDto.setCityID(existingCompany.getAddress().getCityID());
		companyAddressDto.setZipCode(existingCompany.getAddress().getZipCode());
		companyAddressDto.setPhoneCountryID(existingCompany.getAddress().getPhoneCountryID());
		companyAddressDto.setPhone(existingCompany.getAddress().getPhone());
		companyAddressDto.setPhoneExtension(existingCompany.getAddress().getPhoneExtension());	
		
		
		companyDto.setAddress(companyAddressDto);
		Set<CompanyUserMappingDto> companyUserMappingDtoList=new LinkedHashSet<>();
		Set<CompanyUserMapping> companyUserMappingList=existingCompany.getCompanyUserMapping();
		for (CompanyUserMapping cum : companyUserMappingList) {
			CompanyUserMappingDto companyUserMappingDto=new CompanyUserMappingDto();
			companyUserMappingDto.setCompanyUserMappingId(cum.getCompanyUserMappingId());
			
			CompanyUserDto companyUserDto=new CompanyUserDto();
			companyUserDto.setUserId(cum.getUser().getUserId());
			companyUserDto.setFirstName(cum.getUser().getFirstName());
			companyUserDto.setLastName(cum.getUser().getLastName());
			companyUserDto.setEmail(cum.getUser().getEmail());
			companyUserDto.setUserStatus(cum.getUser().getUserStatus());
			
			UserAddressDto address=new UserAddressDto();
			address.setAddressId(cum.getUser().getAddress().getAddressId());			
			address.setStreet(cum.getUser().getAddress().getStreet());
			address.setCountryId(cum.getUser().getAddress().getCountryId());
			address.setStateID(cum.getUser().getAddress().getStateID());
			address.setCityID(cum.getUser().getAddress().getCityID());
			address.setZipCode(cum.getUser().getAddress().getZipCode());
			address.setPhoneCountryID(cum.getUser().getAddress().getPhoneCountryID());
			address.setPhone(cum.getUser().getAddress().getPhone());
			address.setLandPhoneCountryID(cum.getUser().getAddress().getLandPhoneCountryID());
			address.setLandPhone(cum.getUser().getAddress().getLandPhone());
			address.setLandPhoneExtension(cum.getUser().getAddress().getLandPhoneExtension());
			
			
			
			
			
			companyUserDto.setAddress(address);
			
			companyUserMappingDto.setUser(companyUserDto);
			companyUserMappingDto.setActive(cum.isActive());
			companyUserMappingDto.setExternal(cum.isExternal());
			companyUserMappingDtoList.add(companyUserMappingDto);
		}
		companyDto.setAssociatedUsers(companyUserMappingDtoList);
		return companyDto;
		
	}

}
