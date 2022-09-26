package com.pradeep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.exceptions.ResourceExistsException;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.service.ICompanyService;

@RestController
@RequestMapping("/api/companyusermapping")
public class CompanyUserMappingController {

	@Autowired
	ICompanyService companyService;
	
	@PutMapping("/{companyUserMappingId}/potential")
	public ResponseEntity<CompanyUserMapping> updateCompany(
			@PathVariable("companyUserMappingId") Long companyUserMappingId,
			@RequestBody CompanyUserMapping companyusermapping) throws ResourceExistsException, ResourceNotFoundException {
		companyService.updateCompany(companyUserMappingId,companyusermapping);
		return ResponseEntity.noContent().build();
	}
}
