package com.pradeep.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyAddress;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.dtos.UserCompanyMapDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.ICompanyAddressRepository;
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
	private ICompanyAddressRepository companyAddressRepository;	
	
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
	public CompanyUserMapping createUser(CompanyUserMapping companyUserMapping, Long companyid) throws ResourceNotFoundException {
		Company savedcompany = companyRepository.findById(companyid).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyid));
		User saveduser=userRepository.save(getUpdatedUserInfo(companyUserMapping.getUser()));
		companyUserMapping.setCompany(savedcompany);
		companyUserMapping.setUser(saveduser);
		return companyUserMappingRepository.save(companyUserMapping);
	}

	@Override
	public CompanyDto getCompanyInfoById(Long companyid) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyid).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyid));
		CompanyDto companyDto=modelMapper.map(company, CompanyDto.class);
		return companyDto;
	}


	@Override
	public List<UserCompanyMapDto> getCompanyUsersById(Long companyid) {
		List<CompanyUserMapping> companyUserMappingEntities=companyUserMappingRepository.getUserMappingByCompanyId(companyid);
		return companyUserMappingEntities.stream().map(post -> modelMapper.map(post, UserCompanyMapDto.class))
				.collect(Collectors.toList());		
	}

	@Override
	public CompanyAddress addCompanyAddress(CompanyAddress companyAddress, Long companyid) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyid).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyid));
		companyAddress.setCompany(company);
		return companyAddressRepository.save(companyAddress);
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
