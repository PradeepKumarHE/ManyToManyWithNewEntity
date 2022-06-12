package com.pradeep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pradeep.dtos.AddUserRequestDTo;
import com.pradeep.dtos.CompanyDto;
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

	@Autowired
	private CompanyServiceImpl cs;

	@Override
	public CompanyUserMapping createCompany(CompanyUserMapping companyusermapping) {
		CompanyUserMapping savedCompanyUserMapping = companyUserMappingRepository.save(companyusermapping);
		return savedCompanyUserMapping;
	}

	@Override
	public CompanyUserMapping addUserToExistingCompany(Long companyId, AddUserRequestDTo addUserRequestDTo) throws ResourceNotFoundException, UserAssociatedToCompanyException {
		Company existingCompany = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company not found"));
		Optional<User> optionalUser=userRepository.findByEmail(addUserRequestDTo.getEmail());
		if(optionalUser.isPresent()) {
			User existingUser=optionalUser.get();
			Optional<CompanyUserMapping> optionalCompanyUserMapping=companyUserMappingRepository.findByCompanyAndUser(existingCompany,existingUser);
			if(optionalCompanyUserMapping.isPresent()) {
				throw new UserAssociatedToCompanyException("User exists for this company");
			}
			CompanyUserMapping companyUserMapping=getUpdateCompanyUserMappingObj(existingCompany,existingUser);
			
			companyUserMapping.setRole(addUserRequestDTo.getRole());
			companyUserMapping.setAuthorities(addUserRequestDTo.getAuthorities());
			companyUserMapping.setExternal(addUserRequestDTo.isExternal());
			companyUserMapping.setCustomerId(addUserRequestDTo.getCustomerId());
			return companyUserMappingRepository.save(companyUserMapping);
		}
		User user=new User();
		
		user.setEmail(addUserRequestDTo.getEmail());
		user.setEncryptedEmail(addUserRequestDTo.getEncryptedEmail());
		user.setUsername(addUserRequestDTo.getUsername());
		user.setUserStatus(addUserRequestDTo.getUserStatus());
		user.setAddress(addUserRequestDTo.getAddress());
		CompanyUserMapping companyUserMapping=getUpdateCompanyUserMappingObj(existingCompany,user);
		companyUserMapping.setRole(addUserRequestDTo.getRole());
		companyUserMapping.setAuthorities(addUserRequestDTo.getAuthorities());
		companyUserMapping.setExternal(addUserRequestDTo.isExternal());
		companyUserMapping.setCustomerId(addUserRequestDTo.getCustomerId());
		return companyUserMappingRepository.save(companyUserMapping);
	}

	@Override
	public List<CompanyDto> listPotentialCompanies() {
		List<Company> list=companyRepository.findBycompanyStatus(3);
		List<CompanyDto> list1=new ArrayList<CompanyDto>();
		for(Company c:list){
			list1.add(cs.getConsolidatedCompanyData(c));

		}
		return list1;
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