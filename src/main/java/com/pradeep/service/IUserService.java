package com.pradeep.service;

import com.pradeep.domain.User;
import com.pradeep.dtos.UpdateMobileDto;
import com.pradeep.dtos.UpdatePasswordDto;
import com.pradeep.dtos.UserAssociatedCompanyDto;
import com.pradeep.dtos.UserInvitationDto;
import com.pradeep.enums.UserVerificationContexts;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.exceptions.UserVerificationException;
import com.pradeep.responses.Response1;
import com.pradeep.responses.Response2;

import java.util.List;

public interface IUserService {

	User getUserinfoById(Long userid) throws ResourceNotFoundException;

	List<UserAssociatedCompanyDto> getUserAssociatedCompaniesById(Long userid);

	Response1 inviteUserById(UserInvitationDto userInvitationDto) throws ResourceNotFoundException;

	Response2 verifyRegistrationInvitationStatus(String encryptedEmail,String trinyString,UserVerificationContexts registrationInvite) throws UserVerificationException;

	Response1 updateMobile(String encryptedEmail,UpdateMobileDto updateMobileDto) throws UserVerificationException, ResourceNotFoundException;

	Response1 validateOTP(String encryptedEmail,String trinyString, UserVerificationContexts otp) throws UserVerificationException;

	Response1 updatePassword(UpdatePasswordDto updatePasswordDto) throws ResourceNotFoundException;


}
