package com.pradeep.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.ExternalCompanyAddress;
import com.pradeep.domain.User;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.ICompanyRepository;
import com.pradeep.repositories.ICompanyUserMappingRepository;
import com.pradeep.repositories.IExternalCompanyRepository;
import com.pradeep.repositories.IUserRepository;
import com.pradeep.util.ConversionUtil;
import com.pradeep.util.EncryptionDecryption;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private ICompanyRepository companyRepository;
	
	@Autowired
	private IExternalCompanyRepository externalCompanyRepository;
	
	@Autowired
	private ICompanyUserMappingRepository companyUserMappingRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public CompanyUserMapping createCompany(CompanyUserMapping companyusermapping) {
		Company savedCompany=companyRepository.save(getUpdatedCompanyinfo(companyusermapping.getCompany(),companyusermapping.getUser()));
		User saveduser=userRepository.save(getUpdatedUserInfo(companyusermapping.getUser()));		
		return companyUserMappingRepository.save(getUpdatedCompanyUserMapping(savedCompany,saveduser,companyusermapping));
	}


	@Override
	public List<CompanyUserMappingDto> getCompanyListByStatus(Integer companystatus) {
		List<CompanyUserMapping> companyUserMappingEntities=companyUserMappingRepository.getCompanyListByCompanyStatus(companystatus);		
		return companyUserMappingEntities.stream().map(post -> modelMapper.map(post, CompanyUserMappingDto.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public ExternalCompany createExternalCompany(ExternalCompany externalCompany,Long companyid) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyid).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyid));
		externalCompany.setParentCompany(company);		
		ExternalCompany savedExternalCompany=externalCompanyRepository.save(externalCompany);
		return savedExternalCompany;
	}

	@Override
	public User createUser(User user, Long companyid) {
		return null;
	}

	@Override
	public CompanyDto getCompanyInfoById(Long companyid) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyid).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyid));
		CompanyDto companyDto=modelMapper.map(company, CompanyDto.class);
		return companyDto;
	}


	@Override
	public User getCompanyUsersById(Long companyid) {
		return null;
	}

	private Company getUpdatedCompanyinfo(Company company,User user) {		
		company.setActive(Boolean.TRUE);
		company.setCompanyEmailDomain(ConversionUtil.getEmailDomain(user.getEmail()));
		return company;
	}
	
	private User getUpdatedUserInfo(User user) {
		user.setEncryptedEmail(EncryptionDecryption.getSha3EncryptedString(user.getEmail()));
		user.setUsername(ConversionUtil.flattenUserName(user.getEmail()));
		user.setUserStatus("Registered");
		return user;
	}
	
	private CompanyUserMapping getUpdatedCompanyUserMapping(Company savedCompany, User saveduser,CompanyUserMapping companyusermapping) {
		companyusermapping.setCompany(savedCompany);
		companyusermapping.setUser(saveduser);
		companyusermapping.setRole("ROLE_ADMIN");
		String [] authorities= {"create","read"};
		companyusermapping.setAuthorities(authorities);
		companyusermapping.setWorklocaction(savedCompany.getCompanyaddress().get(0));
		return companyusermapping;
	}	
}
