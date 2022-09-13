package com.pradeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.ExternalCompanyAddress;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.repositories.IExternalCompanyAddressRepository;
import com.pradeep.repositories.IExternalCompanyRepository;

@Service
public class ExternalCompanyServicesImpl implements IExternalCompanyServices {

	@Autowired
	private IExternalCompanyRepository externalCompanyRepository;
	
	@Autowired
	private IExternalCompanyAddressRepository externalCompanyAddressRepository;
	
	@Override
	public ExternalCompany getExternalCompanyById(Long externalcompanyid) throws ResourceNotFoundException {
		ExternalCompany externalCompany = externalCompanyRepository.findById(externalcompanyid).orElseThrow(() -> new ResourceNotFoundException("External Company not found :: " + externalcompanyid));
		return externalCompany;
	}

	@Override
	public ExternalCompanyAddress createExternalCompanyAddress(Long externalcompanyid,
			ExternalCompanyAddress externalCompanyAddress) throws ResourceNotFoundException {
		ExternalCompany externalCompany = externalCompanyRepository.findById(externalcompanyid).orElseThrow(() -> new ResourceNotFoundException("External Company not found :: " + externalcompanyid));
		externalCompanyAddress.setExternalcompany(externalCompany);
		return externalCompanyAddressRepository.save(externalCompanyAddress);
	}

}
