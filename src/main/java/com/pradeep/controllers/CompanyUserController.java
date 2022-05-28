package com.pradeep.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;
import com.pradeep.dtos.CompanyDto;
import com.pradeep.dtos.UserDto;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.exceptions.UserAssociatedToCompanyException;
import com.pradeep.service.ICompanyService;
import com.pradeep.service.ICompanyUserMappingService;
import com.pradeep.service.IUserService;

import java.util.List;

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
		CompanyUserMapping companyUserMapping = companyUserMappingService.createCompany(companyusermapping);
		return new ResponseEntity<CompanyUserMapping>(companyusermapping, HttpStatus.CREATED);
	}

	@PostMapping("/company/{companyId}")
	public ResponseEntity<CompanyUserMapping> addUserToExistingCompany(@PathVariable("companyId") Long companyId,@RequestBody User user) throws UserAssociatedToCompanyException, ResourceNotFoundException {
		CompanyUserMapping savedcompanyUserMapping = companyUserMappingService.addUserToExistingCompany(companyId,user);
		return new ResponseEntity<CompanyUserMapping>(savedcompanyUserMapping, HttpStatus.CREATED);
	}
	
	@GetMapping("/company/{companyId}")
	public ResponseEntity<CompanyDto> getCompanyById(@PathVariable("companyId") Long companyId) throws ResourceNotFoundException {
		CompanyDto company = companyService.getCompanyById(companyId);
		return new ResponseEntity<CompanyDto>(company, HttpStatus.OK);
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
		UserDto user = userService.getUserById(userId);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}


	@PutMapping("potential")
	public ResponseEntity<String> updatePotentialCompany(@RequestBody CompanyUserMapping companyusermapping) throws ResourceNotFoundException {

		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	@GetMapping("list/potential")
	public ResponseEntity<List<CompanyUserMapping>> listPotentialCompanies() throws ResourceNotFoundException {
		List<CompanyUserMapping> list=companyUserMappingService.listPotentialCompanies();
		return new ResponseEntity<List<CompanyUserMapping>>(list, HttpStatus.OK);
	}
}