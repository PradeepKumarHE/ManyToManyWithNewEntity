package com.pradeep.service;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;

public interface IUserService {

	User getUserinfoById(Long userid);

	ExternalCompany getUserAssociatedCompaniesById(Long userid);


}
