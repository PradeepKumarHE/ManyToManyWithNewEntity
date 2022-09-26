package com.pradeep.service;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.domain.UserConformation;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.responses.Response1;

public interface IUserService {

	User getUserinfoById(Long userid);

	ExternalCompany getUserAssociatedCompaniesById(Long userid);

	Response1 inviteUserById(Long userid,UserConformation userConformation) throws ResourceNotFoundException;

	Response1 verifyRegistrationInvitationStatus(Integer shortcode) throws ResourceNotFoundException;


}
