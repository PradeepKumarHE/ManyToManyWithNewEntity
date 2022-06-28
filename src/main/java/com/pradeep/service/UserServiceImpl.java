package com.pradeep.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;
import com.pradeep.dtos.CompanyAddressDto;
import com.pradeep.dtos.UserAddressDto;
import com.pradeep.dtos.UserCompanyDto;
import com.pradeep.dtos.UserCompanyMappingDto;
import com.pradeep.dtos.UserDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public User findByEmail(String email) throws ResourceNotFoundException {
		Optional<User> optionalUser=userRepository.findByEmail(email);
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("User not found for the email :: "+email);
		}
		return optionalUser.get();
	}

	@Override
	public UserDto getUserById(Long userId) throws ResourceNotFoundException {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for the userId :: "+userId));
		return getConslidatedUserData(existingUser);
	}
	
	private UserDto getConslidatedUserData(User existingUser) {
		Set<UserCompanyMappingDto> associatedCompanies=new HashSet<>();
		
		UserDto userDto=new UserDto();
		BeanUtils.copyProperties(existingUser, userDto);
		
		UserAddressDto uaddress=new UserAddressDto();
		BeanUtils.copyProperties(existingUser.getAddress(), uaddress);
		
		userDto.setAddress(uaddress);
		
		Set<CompanyUserMapping> companyUserMapping=existingUser.getCompanyUserMapping();
		
		for (CompanyUserMapping eachCompanyUserMapping : companyUserMapping) {
			
			UserCompanyMappingDto userCompanyMappingDto=new UserCompanyMappingDto();
			
			UserCompanyDto userCompanyDto=new UserCompanyDto();		
			BeanUtils.copyProperties(eachCompanyUserMapping.getCompany(), userCompanyDto);
			
			
			CompanyAddressDto address=new CompanyAddressDto();
			BeanUtils.copyProperties(eachCompanyUserMapping.getCompany().getAddress(), address);
			
			userCompanyDto.setAddress(address);
			userCompanyMappingDto.setCompany(userCompanyDto);
			
			BeanUtils.copyProperties(eachCompanyUserMapping, userCompanyMappingDto);
			
			associatedCompanies.add(userCompanyMappingDto);
		}
		userDto.setAssociatedCompanies(associatedCompanies);
		return userDto;
	}
}
