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
import com.pradeep.service.ICompanyService;
import com.pradeep.service.ICompanyUserMappingService;
import com.pradeep.service.IUserService;

@RestController
@RequestMapping("/api")
public class CompanyUserController {

	@Autowired
	ICompanyUserMappingService companyUserMappingService;

	@Autowired
	ICompanyService companyService;

	@Autowired
	IUserService userService;

	@PostMapping("/company")
	public ResponseEntity<CompanyUserMapping> createCompany(@RequestBody CompanyUserMapping companyusermapping) throws JsonProcessingException {
		CompanyUserMapping savedCompanyUserMapping = companyUserMappingService.createCompany(companyusermapping);
		return new ResponseEntity<CompanyUserMapping>(savedCompanyUserMapping, HttpStatus.CREATED);
	}
	
	@GetMapping("/company/{companystatus}")
	public ResponseEntity<List<CompanyUserMapping>> getCompanyList(@PathVariable("companystatus")  Integer companystatus)  {
		List<CompanyUserMapping> savedCompanyUserMapping = companyUserMappingService.getCompanyList(companystatus);
		return new ResponseEntity<List<CompanyUserMapping>>(savedCompanyUserMapping, HttpStatus.OK);
	}

	
}