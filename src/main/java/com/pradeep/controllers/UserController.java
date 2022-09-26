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
import com.pradeep.domain.User;
import com.pradeep.domain.UserConformation;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.responses.Response1;
import com.pradeep.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/{userid}")
	public ResponseEntity<User> getUserinfoById(@PathVariable("userid") Long userid) throws ResourceNotFoundException {
		User savedCompanyUserMapping = userService.getUserinfoById(userid);
		return new ResponseEntity<User>(savedCompanyUserMapping, HttpStatus.OK);
	}
	
	@GetMapping("/{userid}/associatedcompanies")
	public ResponseEntity<ExternalCompany> getUserAssociatedCompaniesById(@PathVariable("userid") Long userid) throws ResourceNotFoundException {
		ExternalCompany savedCompanyUserMapping = userService.getUserAssociatedCompaniesById(userid);
		return new ResponseEntity<ExternalCompany>(savedCompanyUserMapping, HttpStatus.OK);
	}
	
	@PostMapping("/{userid}/invite")
	public ResponseEntity<Response1> inviteUserById(@PathVariable("userid") Long userid,@RequestBody UserConformation userConformation) throws ResourceNotFoundException {
		Response1 response = userService.inviteUserById(userid,userConformation);
		return new ResponseEntity<Response1>(response, HttpStatus.OK);
	}	
	
	@GetMapping("/register-invite-status/{shortcode}")
	public ResponseEntity<Response1> verifyRegistrationInvitationStatus(@PathVariable("shortcode") Integer shortcode) throws ResourceNotFoundException {
		Response1 response =  userService.verifyRegistrationInvitationStatus(shortcode);
		return new ResponseEntity<Response1>(response, HttpStatus.OK);
	}
}