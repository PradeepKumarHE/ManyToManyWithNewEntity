package com.pradeep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;
import com.pradeep.repositories.ICompanyRepository;
import com.pradeep.repositories.ICompanyUserMappingRepository;
import com.pradeep.repositories.IUserRepository;
import com.pradeep.util.ConversionUtil;
import com.pradeep.util.EncryptionDecryption;

@Service
public class CompanyUserMappingServiceImpl implements ICompanyUserMappingService {
	
	@Autowired
	private ICompanyUserMappingRepository companyUserMappingRepository;
	
	@Autowired
	private ICompanyRepository companyRepository;
	
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private CompanyServiceImpl cs;

	@Override
	public CompanyUserMapping createCompany(CompanyUserMapping companyusermapping) {
		Company savedCompany=companyRepository.save(getUpdatedCompanyinfo(companyusermapping.getCompany(),companyusermapping.getUser()));
		User saveduser=userRepository.save(getUpdatedUserInfo(companyusermapping.getUser()));		
		return companyUserMappingRepository.save(getUpdatedCompanyUserMapping(savedCompany,saveduser,companyusermapping));
	}
	
	@Override
	public List<CompanyUserMapping> getCompanyList(Integer companystatus) {
		List<CompanyUserMapping> cs=companyUserMappingRepository.getCompanyListByCompanyStatus();
		return cs;
	}

	private CompanyUserMapping getUpdatedCompanyUserMapping(Company savedCompany, User saveduser,CompanyUserMapping companyusermapping) {
		companyusermapping.setCompany(savedCompany);
		companyusermapping.setUser(saveduser);
		companyusermapping.setDesignation("Owner");
		companyusermapping.setRole("ROLE_ADMIN");
		String [] authorities= {"create","read"};
		companyusermapping.setAuthorities(authorities);
		companyusermapping.setWorklocaction(savedCompany.getAddress());
		return companyusermapping;
	}

	private User getUpdatedUserInfo(User user) {
		user.setEncryptedEmail(EncryptionDecryption.getSha3EncryptedString(user.getEmail()));
		user.setUsername(ConversionUtil.flattenUserName(user.getEmail()));
		user.setUserStatus("Registered");
		return user;
	}

	private Company getUpdatedCompanyinfo(Company company,User user) {
		company.setCompanyStatus(0);
		company.setActive(Boolean.TRUE);
		company.setCompanyEmailDomain(ConversionUtil.getEmailDomain(user.getEmail()));
		return company;
	}

	
	

	
}