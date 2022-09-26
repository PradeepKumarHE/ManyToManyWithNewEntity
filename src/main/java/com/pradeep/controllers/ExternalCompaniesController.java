package com.pradeep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.ExternalCompanyAddress;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.service.IExternalCompanyServices;

@RestController
@RequestMapping("/api/externalcompanies")
public class ExternalCompaniesController {
	
	@Autowired
	private IExternalCompanyServices externalCompaniesServices;	

	@GetMapping("/{externalcompanyid}")
	public ResponseEntity<ExternalCompany> getExternalCompanyById(@PathVariable("externalcompanyid") Long externalcompanyid) throws ResourceNotFoundException {
		ExternalCompany savedCompanyUserMapping = externalCompaniesServices.getExternalCompanyById(externalcompanyid);
		return new ResponseEntity<ExternalCompany>(savedCompanyUserMapping, HttpStatus.OK);
	}
	
	@PostMapping("/{externalcompanyid}")
	public ResponseEntity<ExternalCompanyAddress> createExternalCompanyAddress(@PathVariable("externalcompanyid") Long externalcompanyid,@RequestBody ExternalCompanyAddress externalCompanyAddress) throws ResourceNotFoundException {
		ExternalCompanyAddress savedCompanyUserMapping = externalCompaniesServices.createExternalCompanyAddress(externalcompanyid,externalCompanyAddress);
		return new ResponseEntity<ExternalCompanyAddress>(savedCompanyUserMapping, HttpStatus.OK);
	}
}
