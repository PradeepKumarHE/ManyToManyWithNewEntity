package com.pradeep.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
		
		userDto.setUserId(existingUser.getUserId());
		userDto.setFirstName(existingUser.getFirstName());
		userDto.setLastName(existingUser.getLastName());		
		userDto.setEmail(existingUser.getEmail());
		userDto.setUserStatus(existingUser.getUserStatus());
		
		UserAddressDto uaddress=new UserAddressDto();
		uaddress.setAddressId(existingUser.getAddress().getAddressId());			
		uaddress.setStreet(existingUser.getAddress().getStreet());
		uaddress.setCountryId(existingUser.getAddress().getCountryId());
		uaddress.setStateID(existingUser.getAddress().getStateID());
		uaddress.setCityID(existingUser.getAddress().getCityID());
		uaddress.setZipCode(existingUser.getAddress().getZipCode());
		uaddress.setPhoneCountryID(existingUser.getAddress().getPhoneCountryID());
		uaddress.setPhone(existingUser.getAddress().getPhone());
		uaddress.setLandPhoneCountryID(existingUser.getAddress().getLandPhoneCountryID());
		uaddress.setLandPhone(existingUser.getAddress().getLandPhone());
		uaddress.setLandPhoneExtension(existingUser.getAddress().getLandPhoneExtension());
		userDto.setAddress(uaddress);
		
		Set<CompanyUserMapping> companyUserMapping=existingUser.getCompanyUserMapping();
		for (CompanyUserMapping companyUserMapping2 : companyUserMapping) {
			UserCompanyMappingDto userCompanyMappingDto=new UserCompanyMappingDto();
			userCompanyMappingDto.setCompanyUserMappingId(companyUserMapping2.getCompanyUserMappingId());
			
			UserCompanyDto userCompanyDto=new UserCompanyDto();		
			
			userCompanyDto.setCompanyId(companyUserMapping2.getCompany().getCompanyId());
			userCompanyDto.setCompanyName(companyUserMapping2.getCompany().getCompanyName());
			
			CompanyAddressDto address=new CompanyAddressDto();
			
			address.setAddressId(companyUserMapping2.getCompany().getAddress().getAddressId());
			address.setStreet(companyUserMapping2.getCompany().getAddress().getStreet());
			address.setCountryId(companyUserMapping2.getCompany().getAddress().getCountryId());
			address.setStateID(companyUserMapping2.getCompany().getAddress().getStateID());
			address.setCityID(companyUserMapping2.getCompany().getAddress().getCityID());
			address.setZipCode(companyUserMapping2.getCompany().getAddress().getZipCode());
			address.setPhoneCountryID(companyUserMapping2.getCompany().getAddress().getPhoneCountryID());
			address.setPhone(companyUserMapping2.getCompany().getAddress().getPhone());
			address.setPhoneExtension(companyUserMapping2.getCompany().getAddress().getPhoneExtension());	
			
			userCompanyDto.setAddress(address);
			userCompanyMappingDto.setCompany(userCompanyDto);
			associatedCompanies.add(userCompanyMappingDto);
		}
		userDto.setAssociatedCompanies(associatedCompanies);
		return userDto;
	}
}
