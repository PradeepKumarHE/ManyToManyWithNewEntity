package com.pradeep.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.CompanyUserMappingDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.service.ICompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
	
	@Autowired
	ICompanyService companyService;
	
	@PostMapping
	public ResponseEntity<CompanyUserMapping> createCompany(@RequestBody CompanyUserMapping companyusermapping) throws JsonProcessingException {
		CompanyUserMapping savedCompanyUserMapping = companyService.createCompany(companyusermapping);
		return new ResponseEntity<CompanyUserMapping>(savedCompanyUserMapping, HttpStatus.CREATED);
	}
	
	@GetMapping("/status/{companystatus}")
	public ResponseEntity<List<CompanyUserMappingDto>> getCompanyList(@PathVariable("companystatus")  Integer companystatus)  {
		List<CompanyUserMappingDto> savedCompanyUserMapping = companyService.getCompanyList(companystatus);
		return new ResponseEntity<List<CompanyUserMappingDto>>(savedCompanyUserMapping, HttpStatus.OK);
	}

	
	@PostMapping("/{companyid}/externalcompanies")
	public ResponseEntity<ExternalCompany> createExternalCompany(@RequestBody ExternalCompany externalCompany,@PathVariable("companyid") Long companyid) throws ResourceNotFoundException {
		ExternalCompany savedCompanyUserMapping = companyService.createExternalCompany(externalCompany,companyid);
		return new ResponseEntity<ExternalCompany>(savedCompanyUserMapping, HttpStatus.CREATED);
	}
	
	@PostMapping("/{companyid}/users")
	public ResponseEntity<User> createUser(@RequestBody User user,@PathVariable("companyid") Long companyid) throws ResourceNotFoundException {
		User savedCompanyUserMapping = companyService.createUser(user,companyid);
		return new ResponseEntity<User>(savedCompanyUserMapping, HttpStatus.CREATED);
	}
	
	@GetMapping("/{companyid}")
	public ResponseEntity<CompanyDto> getCompanyInfoById(@PathVariable("companyid") Long companyid) throws ResourceNotFoundException {
		CompanyDto savedCompanyUserMapping = companyService.getCompanyInfoById(companyid);
		return new ResponseEntity<CompanyDto>(savedCompanyUserMapping, HttpStatus.OK);
	}
	
	@GetMapping("/{companyid}/users")
	public ResponseEntity<User> getCompanyUsersById(@PathVariable("companyid") Long companyid) throws ResourceNotFoundException {
		User savedCompanyUserMapping = companyService.getCompanyUsersById(companyid);
		return new ResponseEntity<User>(savedCompanyUserMapping, HttpStatus.OK);
	}
	
}
