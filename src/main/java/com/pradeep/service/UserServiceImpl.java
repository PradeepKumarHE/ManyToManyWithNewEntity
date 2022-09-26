package com.pradeep.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.domain.UserConformation;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.IUserConformationRepository;
import com.pradeep.repositories.IUserRepository;
import com.pradeep.responses.Response1;
import com.pradeep.util.ConversionUtil;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IUserConformationRepository userConformationRepository;
	

	@Override
	public User getUserinfoById(Long userid) {
		return null;
	}

	@Override
	public ExternalCompany getUserAssociatedCompaniesById(Long userid) {
		return null;
	}

	@Override
	public Response1 inviteUserById(Long userid,UserConformation userConformation) throws ResourceNotFoundException {
		User user = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userid));
		Optional<UserConformation> optionalUserConformation=userConformationRepository.findByUserIdAndVerificationContext(userid, userConformation.getVerificationContext());
		if(optionalUserConformation.isEmpty()) {
			UserConformation newUserConformation=new UserConformation();
			newUserConformation.setUserId(userid);
			newUserConformation.setVerificationContext("REGISTRATION_INVITE");
			newUserConformation.setTinyLink(ConversionUtil.encodeUrl());
			Map<String, Object> details = new HashMap<>();
			details.put("phonecountrycodeid", user.getUseraddress().getPhoneCountryId());
			details.put("phone", user.getUseraddress().getPhone());
			newUserConformation.setDetails(details);
			newUserConformation.setLinkCreationDateTime(LocalDateTime.now());
			userConformationRepository.save(newUserConformation);
		}else {
			UserConformation newUserConformation=optionalUserConformation.get();
			newUserConformation.setTinyLink(ConversionUtil.encodeUrl());
			userConformationRepository.save(newUserConformation);
		}
		
		return new Response1(HttpStatus.OK.value(),"Invited successfully");
	}

	@Override
	public Response1 verifyRegistrationInvitationStatus(Integer shortcode) throws ResourceNotFoundException {
		Optional<UserConformation> optionalUserConformation=userConformationRepository.findByTinyLinkAndVerificationContext(String.valueOf(shortcode),"");
		if(optionalUserConformation.isEmpty()) {
			throw new ResourceNotFoundException("Either an invalid link or the link is no more usable");
		}
		if(LocalDateTime.now().plusMinutes(5).isBefore(optionalUserConformation.get().getLinkCreationDateTime())) {
			throw new ResourceNotFoundException("The link is no more usable");
		}
		return new Response1(HttpStatus.OK.value(),"Valid link");
	}
}
