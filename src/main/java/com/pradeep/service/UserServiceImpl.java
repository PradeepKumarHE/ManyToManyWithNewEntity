package com.pradeep.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.pradeep.dtos.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;
import com.pradeep.domain.UserConformation;
import com.pradeep.enums.UserVerificationContexts;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.exceptions.UserVerificationException;
import com.pradeep.repositories.ICompanyUserMappingRepository;
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
	
	@Autowired
	private ICompanyUserMappingRepository companyUserMappingRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public User getUserinfoById(Long userid) throws ResourceNotFoundException {
		User user = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userid));
		return user;
	}

	@Override
	public List<UserAssociatedCompanyDto> getUserAssociatedCompaniesById(Long userid) {
		List<CompanyUserMapping> mappingList=companyUserMappingRepository.findByUser_UserIdAndIsActive(userid,true);
		List<UserAssociatedCompanyDto> newList = mappingList.stream()
				.map(f -> new UserAssociatedCompanyDto(f.getCompany().getCompanyId(),
						f.getCompany().getCompanyName(),f.getDesignation(),f.getRole(),
						f.getAuthorities(),f.getIsExternal()))
				.collect(Collectors.toList());
		return newList;
	}

	public Response1 inviteUserById(UserInvitationDto userInvitationDto) throws ResourceNotFoundException {
		User inviteeUser = userRepository.findByEncryptedEmail(userInvitationDto.getInvitee()).orElseThrow(() -> new ResourceNotFoundException("Invitee User not found :: " + userInvitationDto.getInvitee()));
		User inviterUser = userRepository.findByEncryptedEmail(userInvitationDto.getInviter()).orElseThrow(() -> new ResourceNotFoundException("Inviter User not found :: " + userInvitationDto.getInviter()));
		boolean result=companyUserMappingRepository.findByUserIdAndIsActive(inviteeUser.getUserId(),true);
		if(result){
			return new Response1(HttpStatus.OK.value(),"Sent association email successfully");
		}
		String tinyString=ConversionUtil.encodeUrl();
		Map<String, Object> details = new HashMap<>();
		details.put("phonecountrycodeid", inviteeUser.getUseraddress().getPhoneCountryId());
		details.put("phone", inviteeUser.getUseraddress().getPhone());
		details.put("encryptedemail", inviteeUser.getEncryptedEmail());
		if(null!=inviteeUser.getFirstName()){
			details.put("firstname", inviteeUser.getFirstName());
			details.put("lastname", inviteeUser.getLastName());
		}else{
			details.put("firstname", null);
			details.put("lastname", null);
		}
		addUpdatedUserConformationData(userInvitationDto.getInvitee(),UserVerificationContexts.REGISTRATION_INVITE, tinyString, details);
		sendAdminInvitationEmailWithInviter(inviteeUser,inviterUser,tinyString);
		return new Response1(HttpStatus.OK.value(),"Invited successfully");
	}

	@Override
	public Response2 verifyRegistrationInvitationStatus(String encryptedEmail,String tinystring, UserVerificationContexts registrationInvite) throws UserVerificationException {
		Optional<UserConformation> optionalUserConformation=userConformationRepository.findByEncryptedEmailAndVerificationContextAndTinyString(encryptedEmail,registrationInvite,tinystring);
		if(optionalUserConformation.isEmpty()) {
			throw new UserVerificationException("Either an invalid link or the link is no more usable");
		}
		Duration diff = Duration.between(optionalUserConformation.get().getLinkCreationDateTime(), LocalDateTime.now());
		if(diff.toMinutes()>5) {
			throw new UserVerificationException("The link is no more usable");
		}
		return new Response2(HttpStatus.OK.value(),"Valid link",optionalUserConformation.get().getDetails());
	}

	@Override
	public Response1 validateOTP(String encryptedEmail,String tinystring, UserVerificationContexts otp) throws UserVerificationException {
		Optional<UserConformation> optionalUserConformation=userConformationRepository.findByEncryptedEmailAndVerificationContextAndTinyString(encryptedEmail,UserVerificationContexts.OTP,tinystring);
		if(optionalUserConformation.isEmpty()) {
			throw new UserVerificationException("Either an invalid link or the link is no more usable");
		}
		Duration diff = Duration.between(optionalUserConformation.get().getLinkCreationDateTime(), LocalDateTime.now());
		if(diff.toMinutes()>5) {
			throw new UserVerificationException("The link is no more usable");
		}
		return new Response1(HttpStatus.OK.value(),"Valid OTP");
	}

	@Override
	public Response1 updateMobile(String encryptedEmail,UpdateMobileDto updateMobileDto) throws UserVerificationException, ResourceNotFoundException {
		verifyRegistrationInvitationStatus(encryptedEmail,updateMobileDto.getTinystring(),UserVerificationContexts.REGISTRATION_INVITE);
		User user = userRepository.findByEncryptedEmail(encryptedEmail).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + encryptedEmail));
		user.setUserStatus("OTP_REQUESTED");
		user.setFirstName(updateMobileDto.getFirstname());
		user.setLastName(updateMobileDto.getLastname());
		user.getUseraddress().setPhoneCountryId(updateMobileDto.getPhoneCountryId());
		user.getUseraddress().setPhone(updateMobileDto.getPhone());		
		userRepository.save(user);
		
		String tinyString=ConversionUtil.encodeUrl();
		Map<String, Object> details = new HashMap<>();
		details.put("userid", encryptedEmail);
		details.put("otp", tinyString);
		addUpdatedUserConformationData(encryptedEmail,UserVerificationContexts.OTP, tinyString, details);
		
		notifyThroughEmail(user.getEmail(),tinyString);
		return  new Response1(HttpStatus.OK.value(),"Mobile Number updated successfully");
	}

	@Override
	public Response1 updatePassword(UpdatePasswordDto updatePasswordDto) throws ResourceNotFoundException {
		User user = userRepository.findById(updatePasswordDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + updatePasswordDto.getUserId()));
		Optional<CompanyUserMapping> optionalCompanyUserMapping=companyUserMappingRepository.findByCompanyIdAndUserId(updatePasswordDto.getCompanyId(),updatePasswordDto.getUserId());
		if(optionalCompanyUserMapping.isEmpty()){
			throw new ResourceNotFoundException("User not found");
		}
		user.setPassword("DEFAULT");
		user.setUserStatus("ACTIVE");
		userRepository.save(user);
		CompanyUserMapping companyUserMapping=optionalCompanyUserMapping.get();
		companyUserMapping.setIsActive(true);
		companyUserMappingRepository.save(companyUserMapping);
		return new Response1(HttpStatus.OK.value(),"Password updated");
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
	
	private void notifyThroughEmail(String email,String otp) {
		 Emaildto emaildto=new Emaildto();
	        String[] sa = new String[1];
	        sa[0] = email;
	        emaildto.setTo(sa);
	        emaildto.setCc(new String[0]);
	        emaildto.setSubject("Otp Verification");
	        emaildto.setTemplateName("otp-template.ftl");
	        Map<String, Object> email_process_values = new HashMap<>();
	        email_process_values.put("otp",otp);	    
	        emaildto.setTemplateValues(email_process_values);
	        notificationResources.sendEmail(emaildto);
	}

	private void addUpdatedUserConformationData(String encryptedEmail,UserVerificationContexts verificationContext,String tinyString,Map<String, Object> details) {
		Optional<UserConformation> optionalUserConformation=userConformationRepository.findByEncryptedEmailAndVerificationContext(encryptedEmail, verificationContext);
		if(optionalUserConformation.isEmpty()) {
			UserConformation newUserConformation=new UserConformation();
			newUserConformation.setEncryptedEmail(encryptedEmail);
			newUserConformation.setVerificationContext(verificationContext);
			newUserConformation.setTinyString(tinyString);		
			newUserConformation.setDetails(details);
			newUserConformation.setLinkCreationDateTime(LocalDateTime.now());
			userConformationRepository.save(newUserConformation);
		}else {
			UserConformation newUserConformation=optionalUserConformation.get();
			newUserConformation.setTinyString(tinyString);
			newUserConformation.setLinkCreationDateTime(LocalDateTime.now());
			userConformationRepository.save(newUserConformation);
		}
	}
}
