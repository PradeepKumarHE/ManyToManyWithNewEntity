package com.pradeep.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.exceptions.UserAssociatedToCompanyException;
import com.pradeep.repositories.ICompanyRepository;
import com.pradeep.repositories.ICompanyUserMappingRepository;
import com.pradeep.repositories.IUserRepository;

@Service
public class CompanyUserMappingServiceImpl implements ICompanyUserMappingService {
	
	@Autowired
	private ICompanyUserMappingRepository companyUserMappingRepository;
	
	@Autowired
	private ICompanyRepository companyRepository;
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public CompanyUserMapping createCompany(CompanyUserMapping companyusermapping) {
		CompanyUserMapping savedCompanyUserMapping = companyUserMappingRepository.save(companyusermapping);
		return savedCompanyUserMapping;
	}

	@Override
	public CompanyUserMapping addUserToExistingCompany(Long companyId, User user) throws ResourceNotFoundException, UserAssociatedToCompanyException {
		Company existingCompany = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company not found"));
		Optional<User> optionalUser=userRepository.findByEmail(user.getEmail());
		if(optionalUser.isPresent()) {
			User existingUser=optionalUser.get();
			Optional<CompanyUserMapping> optionalCompanyUserMapping=companyUserMappingRepository.findByCompanyAndUser(existingCompany,existingUser);
			if(optionalCompanyUserMapping.isPresent()) {
				throw new UserAssociatedToCompanyException("User exists for this company");
			}
			CompanyUserMapping companyUserMapping=getUpdateCompanyUserMappingObj(existingCompany,existingUser);
			return companyUserMappingRepository.save(companyUserMapping);
		}
		CompanyUserMapping companyUserMapping=getUpdateCompanyUserMappingObj(existingCompany,user);
		return companyUserMappingRepository.save(companyUserMapping);
	}
	
	private CompanyUserMapping getUpdateCompanyUserMappingObj(Company company,User user) {
		CompanyUserMapping companyUserMapping =new CompanyUserMapping();
		companyUserMapping.setCompany(company);
		companyUserMapping.setUser(user);
		companyUserMapping.setActive(true);
		companyUserMapping.setExternal(false);
		return companyUserMapping;
	}
}