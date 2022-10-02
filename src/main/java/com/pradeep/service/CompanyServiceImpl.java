package com.pradeep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyAddress;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.domain.UserAddress;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.dtos.UserCompanyMapDto;
import com.pradeep.exceptions.ResourceExistsException;
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
	public CompanyUserMapping createCompany(CompanyUserMapping companyusermapping) throws ResourceExistsException {
		Optional<Company> optionalCompany=companyRepository.findByCompanyEmailDomain(ConversionUtil.getEmailDomain(companyusermapping.getUser().getEmail()));
		if(optionalCompany.isPresent()) {
			throw new ResourceExistsException("Company Exists");
		}
		
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
	public ExternalCompany createExternalCompany(ExternalCompany externalCompany,Long companyid) throws ResourceNotFoundException, ResourceExistsException {
		Company company = companyRepository.findById(companyid).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyid));
		findExternalCompanyName(externalCompany,companyid);
		externalCompany.setParentCompany(company);		
		ExternalCompany savedExternalCompany=externalCompanyRepository.save(externalCompany);
		return savedExternalCompany;
	}

	private void findExternalCompanyName(ExternalCompany externalCompany, Long companyid) throws ResourceExistsException {
		Optional<ExternalCompany> optionalExternalCompany = externalCompanyRepository.findByCompanyNameAndParentCompanyId(externalCompany.getCompanyName(), companyid);
		if (optionalExternalCompany.isPresent()) {
			throw new ResourceExistsException("Company Exists");
		}

	}

	@Override
	public CompanyUserMapping createUser(CompanyUserMapping companyUserMapping, Long companyid) throws ResourceNotFoundException, ResourceExistsException {
		Optional<User> optionalUser =findUserByEmail(companyUserMapping.getUser().getEmail(),companyid); 
		Company savedcompany = companyRepository.findById(companyid).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyid));
		companyUserMapping.setCompany(savedcompany);
		if(optionalUser.isPresent()) {
			companyUserMapping.setUser(optionalUser.get());
		}else {
			User saveduser=userRepository.save(getUpdatedUserInfo(companyUserMapping.getUser()));
			companyUserMapping.setUser(saveduser);
		}
		
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
	
	
	@Override
	public CompanyUserMapping updateCompany(Long companyUserMappingId, CompanyUserMapping companyusermapping) throws ResourceExistsException, ResourceNotFoundException {
		verifyCompanyExistance(companyusermapping);
		CompanyUserMapping savedCompanyUserMapping = companyUserMappingRepository.findById(companyUserMappingId).orElseThrow(() -> new ResourceNotFoundException("Company not found :: " + companyUserMappingId));
		
		String[] ignoreCompanyAddressFields = {"addressId"};
		CompanyAddress companyAddress=companyusermapping.getCompany().getCompanyaddress().get(0);
		CompanyAddress savedCompanyAddress=savedCompanyUserMapping.getCompany().getCompanyaddress().get(0);		
		BeanUtils.copyProperties(companyAddress, savedCompanyAddress, ignoreCompanyAddressFields);		
		List<CompanyAddress> companyaddressList=new ArrayList<CompanyAddress>(); 
		companyaddressList.add(savedCompanyAddress);
		
		String[] ignoreCompanyFields = {"companyId","active","companyUserMapping","externalCompanies","companyaddress"};
		Company company=companyusermapping.getCompany();
		Company savedCompany=savedCompanyUserMapping.getCompany();		
		BeanUtils.copyProperties(company, savedCompany, ignoreCompanyFields);
		savedCompany.setCompanyEmailDomain(ConversionUtil.getEmailDomain(companyusermapping.getUser().getEmail()));
		savedCompany.setCompanyaddress(companyaddressList);
		
		String[] ignoreUserAddressFields = {"useraddressId"};
		UserAddress userAddress=companyusermapping.getUser().getUseraddress();
		UserAddress savedUserAddress=savedCompanyUserMapping.getUser().getUseraddress();		
		BeanUtils.copyProperties(userAddress, savedUserAddress, ignoreUserAddressFields);		
		
		String[] ignoreUserFields = {"userId","companyUserMapping","useraddress"};
		User user=companyusermapping.getUser();
		User savedUser=savedCompanyUserMapping.getUser();		
		BeanUtils.copyProperties(user, savedUser, ignoreUserFields);
		savedUser.setEncryptedEmail(EncryptionDecryption.getSha3EncryptedString(user.getEmail()));
		savedUser.setUsername(ConversionUtil.flattenUserName(user.getEmail()));
		savedUser.setUseraddress(savedUserAddress);
		
		//String[] ignoreCompanyUserMappingFields = {"companyUserMappingId","company","user","worklocaction","externalCompany","externalCompanyAddress"};
		savedCompanyUserMapping.setCompany(savedCompany);
		savedCompanyUserMapping.setUser(savedUser);
		return companyUserMappingRepository.save(savedCompanyUserMapping);
	}
	
	private Company getUpdatedCompanyinfo(Company company,User user){	
		company.setActive(Boolean.TRUE);
		if(company.getCompanyName().equals("Friends Group Services")) {
			company.setCompanyStatus(1);
		}else {
			 company.setCompanyStatus(0);
		}
		company.setCompanyEmailDomain(ConversionUtil.getEmailDomain(user.getEmail()));
		return company;
	}
	
	private User getUpdatedUserInfo(User user) {
		user.setEncryptedEmail(EncryptionDecryption.getSha3EncryptedString(user.getEmail()));
		user.setUsername(ConversionUtil.flattenUserName(user.getEmail()));
		user.setUserStatus("REFU");
		return user;
	}
	
	private CompanyUserMapping getUpdatedCompanyUserMapping(Company savedCompany, User saveduser,CompanyUserMapping companyusermapping) {
		companyusermapping.setCompany(savedCompany);
		companyusermapping.setUser(saveduser);
		companyusermapping.setDesignation("COMPANY_OWNER");
		companyusermapping.setRole("ROLE_ADMIN");
		String [] authorities= {"read","create","delete","update"};
		companyusermapping.setAuthorities(authorities);
		if(savedCompany.getCompanyName().equals("Friends Group Services")) {
			companyusermapping.setIsActive(true);
		}else {
			companyusermapping.setIsActive(false);
		}
		companyusermapping.setIsExternal(false);
		companyusermapping.setWorklocaction(savedCompany.getCompanyaddress().get(0));
		return companyusermapping;
	}

	private void verifyCompanyExistance(CompanyUserMapping companyusermapping) throws ResourceExistsException {
		Optional<Company> optionalCompany=companyRepository.findByCompanyEmailDomain(ConversionUtil.getEmailDomain(companyusermapping.getUser().getEmail()));
		if(optionalCompany.isPresent()) {
			Company company=optionalCompany.get();
			if(company.getCompanyId().longValue() != companyusermapping.getCompany().getCompanyId().longValue()) {
				throw new ResourceExistsException("Company Exists");
			}
		}
	}
	
	private Optional<User> findUserByEmail(String email, Long companyid) throws ResourceExistsException {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			List<CompanyUserMapping> companyUserMappingList = companyUserMappingRepository.getUserMappingByUserId(optionalUser.get().getUserId());
			List<Long> companyIdList = companyUserMappingList.stream().map(obj -> obj.getCompany().getCompanyId()).collect(Collectors.toList());
			if (companyIdList.contains(companyid)) {
				throw new ResourceExistsException("User exists");
			}
			return optionalUser;
		}else {
			return Optional.empty();
		}

	}
}