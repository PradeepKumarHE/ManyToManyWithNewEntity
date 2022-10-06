package com.pradeep.service;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.domain.UserConformation;
import com.pradeep.dtos.UserInvitationDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.responses.Response1;
import com.pradeep.responses.Response2;

public interface IUserService {

	User getUserinfoById(Long userid);

	ExternalCompany getUserAssociatedCompaniesById(Long userid);

	Response1 inviteUserById(UserInvitationDto userInvitationDto) throws ResourceNotFoundException;

	Response2 verifyRegistrationInvitationStatus(Integer shortcode) throws ResourceNotFoundException;


}
