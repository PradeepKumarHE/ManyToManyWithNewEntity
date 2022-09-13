package com.pradeep.service;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.ExternalCompanyAddress;
import com.pradeep.exceptions.ResourceNotFoundException;

public interface IExternalCompanyServices {

	ExternalCompany getExternalCompanyById(Long externalcompanyid) throws ResourceNotFoundException;

	ExternalCompanyAddress createExternalCompanyAddress(Long externalcompanyid, ExternalCompanyAddress externalCompanyAddress) throws ResourceNotFoundException;

}
