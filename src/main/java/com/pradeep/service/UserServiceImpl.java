package com.pradeep.service;

import java.time.Duration;
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
import com.pradeep.dtos.Emaildto;
import com.pradeep.dtos.UserInvitationDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.IUserConformationRepository;
import com.pradeep.repositories.IUserRepository;
import com.pradeep.responses.Response1;
import com.pradeep.responses.Response2;
import com.pradeep.util.ConversionUtil;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IUserConformationRepository userConformationRepository;
	
	@Autowired
	private NotificationResources notificationResources;

	@Override
	public User getUserinfoById(Long userid) {
		return null;
	}

	@Override
	public ExternalCompany getUserAssociatedCompaniesById(Long userid) {
		return null;
	}

	
	public Response1 inviteUserById(UserInvitationDto userInvitationDto) throws ResourceNotFoundException {
		User inviteeUser = userRepository.findById(userInvitationDto.getInviteeUserId()).orElseThrow(() -> new ResourceNotFoundException("Invitee User not found :: " + userInvitationDto.getInviteeUserId()));
		User inviterUser = userRepository.findById(userInvitationDto.getInviterUserId()).orElseThrow(() -> new ResourceNotFoundException("Inviter User not found :: " + userInvitationDto.getInviterUserId()));
		Optional<UserConformation> optionalUserConformation=userConformationRepository.findByUserIdAndVerificationContext(userInvitationDto.getInviteeUserId(), "REGISTRATION_INVITE");
		String randomString=ConversionUtil.encodeUrl();
		if(optionalUserConformation.isEmpty()) {
			UserConformation newUserConformation=new UserConformation();
			newUserConformation.setUserId(userInvitationDto.getInviteeUserId());
			newUserConformation.setVerificationContext("REGISTRATION_INVITE");
			newUserConformation.setTinyLink(randomString);
			Map<String, Object> details = new HashMap<>();
			details.put("phonecountrycodeid", inviteeUser.getUseraddress().getPhoneCountryId());
			details.put("phone", inviteeUser.getUseraddress().getPhone());
			newUserConformation.setDetails(details);
			newUserConformation.setLinkCreationDateTime(LocalDateTime.now());
			userConformationRepository.save(newUserConformation);
		}else {
			UserConformation newUserConformation=optionalUserConformation.get();
			newUserConformation.setTinyLink(randomString);
			userConformationRepository.save(newUserConformation);
		}
		
		sendAdminInvitationEmailWithInviter(inviteeUser,inviterUser,randomString);
		
		return new Response1(HttpStatus.OK.value(),"Invited successfully");
	}

	@Override
	public Response2 verifyRegistrationInvitationStatus(Integer shortcode) throws ResourceNotFoundException {
		Optional<UserConformation> optionalUserConformation=userConformationRepository.findByTinyLinkAndVerificationContext(String.valueOf(shortcode),"REGISTRATION_INVITE");
		if(optionalUserConformation.isEmpty()) {
			throw new ResourceNotFoundException("Either an invalid link or the link is no more usable");
		}
		
		Duration diff = Duration.between(optionalUserConformation.get().getLinkCreationDateTime(), LocalDateTime.now());
		if(diff.toMinutes()>5) {
			throw new ResourceNotFoundException("The link is no more usable");
		}
		return new Response2(HttpStatus.OK.value(),"Valid link",optionalUserConformation.get().getDetails());
	}
	
	public void sendAdminInvitationEmailWithInviter(User invitee,User inviter,String tinyString) {
		Emaildto emaildto=new Emaildto();
        String[] sa = new String[1];
        sa[0] = invitee.getEmail();
        emaildto.setTo(sa);
        emaildto.setCc(new String[0]);
        emaildto.setSubject("Registration completion");
        emaildto.setTemplateName("email-template.ftl");
        Map<String, Object> email_process_values = new HashMap<>();
        email_process_values.put("firstname",invitee.getFirstName());
        email_process_values.put("lastname",invitee.getLastName());
        email_process_values.put("tinyString",tinyString);
        emaildto.setTemplateValues(email_process_values);
        notificationResources.sendEmail(emaildto);
		
	}
}
